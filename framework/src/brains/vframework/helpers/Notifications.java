package brains.vframework.helpers;

import brains.vframework.VMessages;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class Notifications {

    public final static Notification errorNotification;
    public final static Notification warningNotification;
    public final static Notification notification;

    static {
        warningNotification = new Notification(VMessages.getText("warning"), Notification.Type.WARNING_MESSAGE);
        warningNotification.setHtmlContentAllowed(true);
        warningNotification.setDelayMsec(3000);
        warningNotification.setIcon(FontAwesome.WARNING);

        errorNotification = new Notification(VMessages.getText("error"), Notification.Type.ERROR_MESSAGE);
        errorNotification.setHtmlContentAllowed(true);
        errorNotification.setStyleName("error");
        errorNotification.setIcon(FontAwesome.EXCLAMATION_TRIANGLE);

        notification = new Notification("Ok", Notification.Type.HUMANIZED_MESSAGE);
        notification.setHtmlContentAllowed(true);
        notification.setDelayMsec(3000);
        notification.setStyleName("success");
    }

    public static void error(String message) {
        errorNotification.setDescription(message);
        errorNotification.show(UI.getCurrent().getPage());
    }

    public static void message(String message) {
        notification.setDescription(message);
        notification.show(UI.getCurrent().getPage());
    }

    public static void warning(String message) {
        warningNotification.setDescription(message);
        warningNotification.show(UI.getCurrent().getPage());
    }
}
