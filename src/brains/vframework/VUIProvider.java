package brains.vframework;

import brains.vframework.annotation.ViewUI;
import brains.vframework.helpers.Beans;
import brains.vframework.ui.error.V404UI;
import brains.vframework.ui.error.V500UI;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.UI;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VUIProvider extends UIProvider implements Serializable {
    private static Logger logger = Logger.getLogger(VUI.class.getName());
    private static Pattern pathPattern = Pattern.compile("/([\\w\\d]*)(/?|/(.*))");

    @Inject
    private VViewProvider VViewProvider;

    @Override
    public UI createInstance(UICreateEvent event) {
        try {
            return createUI(getUIClass(event.getRequest().getPathInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            return new V500UI(e);
        }
    }

    @Override
    public String getTheme(UICreateEvent event) {
        return VContext.getTheme();
    }

    @Override
    public String getWidgetset(UICreateEvent event) {
        return "brains.vframework.AppWidgetSet";
    }

    @Override
    public String getPageTitle(UICreateEvent event) {
        Title annotation = event.getUIClass().getAnnotation(Title.class);
        if (annotation == null) {
            return "Brains Vframework v2.2";
        }
        return annotation.value();
    }

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return getUIClass(
            event.getRequest().getPathInfo()
        );
    }

    @Override
    public PushMode getPushMode(UICreateEvent event) {
        return PushMode.AUTOMATIC;
    }

    @Override
    public Transport getPushTransport(UICreateEvent event) {
        return Transport.WEBSOCKET;
    }

    private UI createUI(Class<? extends VUI> forClass) {
        logger.log(Level.FINE, "Creating UI for [{0}]", forClass);

        VUI ui = Beans.getBean(forClass);

        if (ui != null && forClass.isAnnotationPresent(ViewUI.class)) {
            Navigator navigator = new Navigator(ui, ui.content());
            navigator.addProvider(VViewProvider);
        }

        return ui;
    }

    public static Class<? extends VUI> getUIClass(String path) {
        if (path == null || path.isEmpty() || path.equals("/")) {
            logger.fine("UI not presented. UI by default will be opened");
            return VContext.getDefaultUI();
        } else {
            Matcher matcher = pathPattern.matcher(path);
            if (!matcher.matches()) return V404UI.class;
            Class<? extends VUI> uiType = VContext.getUIType(matcher.group(1));
            if (uiType == null) return V404UI.class;

            return uiType;
        }
    }

    public static Class<? extends VUI> findUIClass(String path) {
        if (path == null || path.isEmpty() || path.equals("/")) {
            return VContext.getDefaultUI();
        } else {
            Matcher matcher = pathPattern.matcher(path);
            if (!matcher.matches()) return null;

            return VContext.getUIType(matcher.group(1));
        }
    }
}