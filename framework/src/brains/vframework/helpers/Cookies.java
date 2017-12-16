package brains.vframework.helpers;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;

import javax.servlet.http.Cookie;
import java.io.Serializable;

public class Cookies implements Serializable {

    public static Cookie getCookie(VaadinRequest request, String name) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) return cookie;
        }
        return null;
    }

    public static void setCookie(VaadinRequest request, String name, String value) {
        Page.getCurrent().getJavaScript().execute("document.cookie='"+name+"="+value+"; path=/'");
    }
}
