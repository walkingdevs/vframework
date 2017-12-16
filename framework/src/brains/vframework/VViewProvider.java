package brains.vframework;

import brains.vframework.helpers.Beans;
import brains.vframework.view.api.RouteView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import brains.vframework.exception.RouteParseException;
import brains.vframework.presenter.api.RouteViewPresenter;
import brains.vframework.view.route.ErrorView;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VViewProvider implements ViewProvider, Serializable {

    private final Pattern requestPattern = Pattern.compile("([\\w\\d]+)(/?|/(.*))");
    private static final Logger logger = Logger.getLogger(VViewProvider.class.getName());

    @Override
    public String getViewName(String request) {
        return request;
    }

    @Override
    public com.vaadin.navigator.View getView(String request) {
        long start = System.currentTimeMillis();

        if (StringUtils.isBlank(request) || request.equals("/")) return new Navigator.EmptyView();

        Matcher matcher = requestPattern.matcher(request);
        if (!matcher.matches()) return new ErrorView();

        String presenterName = matcher.group(1);
        String requestParams = matcher.group(3);

        RouteView view = new ErrorView();

        try {
            view = viewFor(presenterName, requestParams);
        } catch (RouteParseException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.log(Level.FINE, "--------------------------------------------------------- View timing [{0}]", (end - start));

        return view;
    }

    public <VIEW extends RouteView, PRESENTER extends RouteViewPresenter<VIEW>> RouteView viewFor(final String presenterName, final String requestParams) throws RouteParseException {
        logger.log(Level.FINE, presenterName);

        try {
            Class<VIEW> viewClass = VContext.getViewTypeByPresenterName(presenterName);
            Class<PRESENTER> presenterClass = VContext.getViewModelType(presenterName);

            VIEW view = Beans.getBean(viewClass);
            PRESENTER presenter = Beans.getBean(presenterClass);

            presenter.view(view);
            presenter.onRequest(requestParams);

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorView(e.getMessage());
        }
    }
}
