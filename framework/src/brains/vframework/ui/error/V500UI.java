package brains.vframework.ui.error;

import com.vaadin.server.VaadinRequest;

import java.io.Serializable;

public class V500UI extends ErrorUI implements Serializable {

    public V500UI() {
        error.caption.setValue("500: Server error");
    }

    public V500UI(final Exception e) {
        error.message.setValue(e.getLocalizedMessage());
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    }
}
