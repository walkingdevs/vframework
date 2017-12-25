import brains.vframework.VUI;
import brains.vframework.annotation.DefaultUI;
import brains.vframework.annotation.SecuredUI;
import brains.vframework.annotation.ViewUI;
import brains.vframework.view.ListView;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

@DefaultUI
@ViewUI
@SecuredUI
public class MainUI extends VUI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new VerticalLayout(new ListView()));
    }

    @WebServlet(urlPatterns = "/foo/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
