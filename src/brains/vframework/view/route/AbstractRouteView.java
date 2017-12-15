package brains.vframework.view.route;

import brains.vframework.view.api.RouteView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import brains.vframework.component.VWindow;

import java.io.Serializable;

public abstract class AbstractRouteView extends CustomComponent implements RouteView, Serializable {
    private final VWindow window;

    public AbstractRouteView() {
        setSizeFull();

        window = new VWindow(this);
        window.width("98%").height("98%");
        window.setModal(true);
        window.setResizable(false);
    }

    @Override
    public VWindow getWindow() {
        return window;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}