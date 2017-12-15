package brains.vframework.view.route;

import brains.vframework.view.DefaultCrudView;
import com.vaadin.navigator.ViewChangeListener;
import brains.vframework.component.VWindow;

import java.io.Serializable;

public class EntityExplorerView extends AbstractRouteView implements Serializable {

    public DefaultCrudView defaultCrudView = new DefaultCrudView();

    public EntityExplorerView() {
        setCompositionRoot(defaultCrudView);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public VWindow getWindow() {
        return null;
    }
}
