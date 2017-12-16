package brains.vframework.ui.error;

import brains.vframework.component.Error;
import com.vaadin.server.VaadinRequest;
import brains.vframework.VUI;

import java.io.Serializable;

public class ErrorUI extends VUI implements Serializable {

    public Error error = new Error();

    public ErrorUI() {
        addStyleName("error-ui");
        setContent(error);
    }

    @Override
    protected void init(final VaadinRequest request) {
    }
}
