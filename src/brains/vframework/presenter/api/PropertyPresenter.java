package brains.vframework.presenter.api;

import com.vaadin.data.Validator;
import com.vaadin.ui.Component;
import com.vaadin.addon.jpacontainer.EntityItemProperty;

public interface PropertyPresenter<VIEW extends Component> extends Presenter<VIEW> {

    EntityItemProperty propertyDataSource();
    void propertyDataSource(final EntityItemProperty propertyDataSource);

    void addValidator(final Validator validator);
    void removeValidator(final Validator validator);

    boolean isModified();

    void discard();
}
