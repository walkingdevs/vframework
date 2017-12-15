package brains.vframework;

import com.vaadin.server.VaadinServlet;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@PersistenceContext(name="persistence/em", unitName="default")
public class VServlet extends VaadinServlet implements Serializable {
    @Inject
    VUIProvider vuiProvider;

    @Override
    public void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    @Override
    protected void servletInitialized() throws ServletException {
        getService().addSessionInitListener(sessionInitEvent -> sessionInitEvent.getSession().addUIProvider(vuiProvider));
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().getId();
        super.doGet(req, resp);
    }
}