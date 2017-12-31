import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.guice.annotation.Configuration;
import com.vaadin.guice.server.GuiceVaadinServlet;
import view.MainUI;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/foo/*", name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
@Configuration(modules = SModule.class, basePackages = "view")
public class GuiceApplicationServlet extends GuiceVaadinServlet{
}
