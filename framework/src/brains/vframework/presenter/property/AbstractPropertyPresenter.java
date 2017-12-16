package brains.vframework.presenter.property;

import com.vaadin.addon.jpacontainer.EntityItemProperty;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;
import brains.vframework.presenter.api.PropertyPresenter;

public abstract class AbstractPropertyPresenter<VIEW extends AbstractField> implements PropertyPresenter<VIEW> {

    private VIEW view;
    private EntityItemProperty property;
    private Object initialValue = null;

    @Override
    public EntityItemProperty propertyDataSource() {
        return property;
    }

    @Override
    public void propertyDataSource(final EntityItemProperty propertyDataSource) {
        this.property = propertyDataSource;
        view.setPropertyDataSource(propertyDataSource);
        initialValue = propertyDataSource.getValue();
    }

    @Override
    public boolean isModified() {
        return !(initialValue == null && propertyDataSource().getValue() == null) && (initialValue == null && propertyDataSource().getValue() != null || initialValue != null && propertyDataSource().getValue() == null || !initialValue.equals(propertyDataSource().getValue()));
    }

    @Override
    public void addValidator(final Validator validator) {
        view.addValidator(validator);
    }

    @Override
    public void removeValidator(final Validator validator) {
        view.removeValidator(validator);
    }

    @Override
    public VIEW view() {
        return view;
    }

    @Override
    public void view(final VIEW view) {
        this.view = view;
    }

    @Override
    public void discard() {
    }

    @Override
    public void onReload() {
    }
}
