package brains.vframework.ui.error;

import com.vaadin.server.VaadinRequest;

import java.io.Serializable;

public class V404UI extends ErrorUI implements Serializable {

    public V404UI() {
        System.out.println("----------------------------------------------------------------------------404");
        error.message.setValue("404: UI not found");
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    }
}
