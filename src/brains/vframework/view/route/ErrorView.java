package brains.vframework.view.route;

import brains.vframework.component.Error;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import brains.vframework.component.VWindow;

import java.io.Serializable;

public class ErrorView extends AbstractRouteView implements Serializable {
    public final Error error = new Error();

    public ErrorView(final String errorMsg) {
        super();
        error.message.setCaption(errorMsg);
    }

    public ErrorView() {
        addStyleName("error-view");
        error.message.setValue("404: Page not found");
        setCompositionRoot(new Label("fdsfdsf"));
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public VWindow getWindow() {
        return null;
    }
}
