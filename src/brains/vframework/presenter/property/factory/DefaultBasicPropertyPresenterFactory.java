package brains.vframework.presenter.property.factory;

import brains.vframework.VMessages;
import brains.vframework.presenter.api.PropertyPresenter;
import brains.vframework.presenter.property.*;
import brains.vframework.presenter.property.factory.api.BasicPropertyPresenterFactory;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import javax.persistence.Lob;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultBasicPropertyPresenterFactory implements BasicPropertyPresenterFactory, Serializable {

    private static final Logger logger = Logger.getLogger(DefaultBasicPropertyPresenterFactory.class.getName());

    @Override
    public PropertyPresenter<?> create(final Class<?> forClass, final Field forField) {
        logger.log(Level.FINE, "Creating BasicPropertyPresenterFactory for field [{0}]", forField);

        PropertyPresenter<?> propertyPresenter;

        if (forField.isAnnotationPresent(Lob.class)) {
            propertyPresenter = forLob();
        } else if (Enum.class.isAssignableFrom(forField.getType())) {
            propertyPresenter = forEnum(forField.getType());
        } else if (Date.class.isAssignableFrom(forField.getType())) {
            propertyPresenter = forDate();
        } else if (Boolean.class.isAssignableFrom(forField.getType()) || boolean.class.isAssignableFrom(forField.getType())) {
            propertyPresenter = forBoolean();
        } else {
            propertyPresenter = forString();
        }

        return configure(propertyPresenter, forClass, forField);
    }

    private PropertyPresenter<?> configure(final PropertyPresenter<?> propertyPresenter, final Class<?> forClass, final Field forField) {
        propertyPresenter.view().setSizeFull();
        propertyPresenter.view().setCaption(VMessages.getText(forField.getName()));
        propertyPresenter.addValidator(new BeanValidator(forClass, forField.getName()));

        return propertyPresenter;
    }

    private LobPropertyPresenter forLob() {
        TextArea view = new TextArea();
        view.setHeightUndefined();
        view.setNullRepresentation("");
        view.addStyleName("form-item small");
        view.setImmediate(true);

        LobPropertyPresenter presenter = new LobPropertyPresenter();
        presenter.view(view);

        return presenter;
    }

    private ComboBoxPropertyPresenter forEnum(final Class<?> forClass) {
        ComboBox view = new ComboBox();
        view.setTextInputAllowed(false);
        view.setCaption(forClass.getSimpleName());

        for (Object item : forClass.getEnumConstants()) {
            view.addItem(item);
            view.setItemCaption(item, VMessages.getText(item.toString()));
        }
        view.setWidth("100%");
        view.setNullSelectionAllowed(false);
        view.addStyleName("form-item small");
        view.setImmediate(true);

        ComboBoxPropertyPresenter presenter = new ComboBoxPropertyPresenter();
        presenter.view(view);

        return presenter;
    }

    private DatePropertyPresenter forDate() {
        DateField view = new DateField();
        view.addStyleName("form-item small");
        view.setImmediate(true);
        view.setResolution(Resolution.MINUTE);

        DatePropertyPresenter presenter = new DatePropertyPresenter();
        presenter.view(view);

        return presenter;
    }

    private PropertyPresenter forBoolean() {
        CheckBox view = new CheckBox();
        view.addStyleName("form-item color2");
        view.setImmediate(true);

        CheckBoxPropertyPresenter presenter = new CheckBoxPropertyPresenter();
        presenter.view(view);

        return presenter;
    }

    private PropertyPresenter forString() {
        TextField view = new TextField();
        view.setNullRepresentation("");
        view.addStyleName("form-item small");
        view.setImmediate(true);

        TextPropertyPresenter presenter = new TextPropertyPresenter();
        presenter.view(view);

        return presenter;
    }
}
