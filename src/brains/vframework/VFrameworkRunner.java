package brains.vframework;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class VFrameworkRunner implements ServletContextListener, Serializable {
    private static final Logger LOG = Logger.getLogger(VFrameworkRunner.class.getName());

    @Inject
    VServlet vServlet;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.log(Level.INFO, "Servlet context initialized. Time [{0}]", new Date());

        ServletContext servletContext = servletContextEvent.getServletContext();

        try {
            String theme = servletContextEvent.getServletContext().getInitParameter("theme");
            System.out.println(
                "Using theme: " + theme
            );

            String vframeworkContext = servletContextEvent.getServletContext().getInitParameter("vframeworkContext");
            if (StringUtils.isEmpty(vframeworkContext)) {
                vframeworkContext = "/*";
            }
            System.out.println(
                "Using context: " + vframeworkContext
            );
            ServletRegistration.Dynamic registration = servletContext.addServlet(vframeworkContext, vServlet);
            registration.setAsyncSupported(true);
            registration.addMapping(vframeworkContext, "/VAADIN/*");

            VContext.init(entityManager, theme);
            VMessages.init(servletContext);

            welcome();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.log(Level.INFO, "Servlet context destroyed. Time [{0}]", new Date());
    }

    private void welcome() throws URISyntaxException, IOException {
        for (String s : Welcome.text) {
            System.out.println(s);
        }
    }
}
