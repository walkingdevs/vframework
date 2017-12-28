import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.guice.annotation.PackagesToScan;
import com.vaadin.guice.server.GuiceVaadinServlet;

import javax.servlet.annotation.WebServlet;

//@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
@WebServlet(urlPatterns = "/foo/*", name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
@PackagesToScan("")
public class GuiceApplicationServlet extends GuiceVaadinServlet{
}
