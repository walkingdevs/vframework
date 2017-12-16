package brains.vframework.ui;

import brains.vframework.ui.error.ErrorUI;
import com.vaadin.server.VaadinRequest;

import java.io.Serializable;

public class ITWORKSUI extends ErrorUI implements Serializable {

    public ITWORKSUI() {
        error.caption.setValue("It, works!");
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    }
}