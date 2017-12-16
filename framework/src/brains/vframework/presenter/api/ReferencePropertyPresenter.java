package brains.vframework.presenter.api;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.ui.Component;

public interface ReferencePropertyPresenter<REFERENCE, VIEW extends Component> extends ContainerBacked<EntityContainer<REFERENCE>>, PropertyPresenter<VIEW> {
    void setValue(final REFERENCE value);
    REFERENCE getValue();
}
