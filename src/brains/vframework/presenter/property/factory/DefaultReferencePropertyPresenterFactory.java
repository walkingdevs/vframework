package brains.vframework.presenter.property.factory;

import brains.vframework.VMessages;
import brains.vframework.presenter.property.ManyToOnePropertyPresenter;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.validator.BeanValidator;
import brains.vframework.VContainers;
import brains.vframework.presenter.api.ReferencePropertyPresenter;
import brains.vframework.presenter.property.factory.api.ReferencePropertyPresenterFactory;
import brains.vframework.view.property.ManyToOnePropertyView;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultReferencePropertyPresenterFactory implements ReferencePropertyPresenterFactory, Serializable {

    private static final Logger logger = Logger.getLogger(DefaultReferencePropertyPresenterFactory.class.getName());

    @Override
    public <ITEM, REFERENCE> ReferencePropertyPresenter<REFERENCE, ?> create(final Class<ITEM> forClass, final Field forField) {
        logger.log(Level.FINE, "Creating ReferencePropertyPresenter for field [{0}]", forField);

        JPAContainer container = VContainers.createContainer(forField.getType());

        ManyToOnePropertyPresenter<REFERENCE> presenter = new ManyToOnePropertyPresenter<>();
        presenter.view(new ManyToOnePropertyView());
        presenter.view().setCaption(VMessages.getText(forField.getName()));
        presenter.container(container);

        BeanValidator beanValidator = new BeanValidator(forClass, forField.getName());
        beanValidator.setLocale(VMessages.locale());
        presenter.addValidator(beanValidator);

        return presenter;
    }
}