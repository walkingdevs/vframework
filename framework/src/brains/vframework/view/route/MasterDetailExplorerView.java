package brains.vframework.view.route;

import com.vaadin.navigator.ViewChangeListener;
import brains.vframework.component.VWindow;
import brains.vframework.view.MasterDetailView;

import java.io.Serializable;

public class MasterDetailExplorerView extends AbstractRouteView implements Serializable {

    public MasterDetailView masterDetailView = new MasterDetailView();

    public MasterDetailExplorerView() {
        setCompositionRoot(masterDetailView);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public VWindow getWindow() {
        return null;
    }
}
