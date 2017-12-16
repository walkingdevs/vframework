package brains.vframework;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import brains.vframework.helpers.Cookies;
import brains.vframework.helpers.PropertiesHelper;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VMessages implements Serializable {
    public static final String LOCALE_COOKIE_NAME = "locale";

    private static final Logger logger = Logger.getLogger(VMessages.class.getName());
    private static final Map<String, Map<String, String>> locales = new HashMap<>();

    static {
        locales.put("en", new HashMap<>());
        locales.put("ru", new HashMap<>());
        locales.put("kz", new HashMap<>());
    }

    public static String getLocaleTagFromCookie(VaadinRequest request) {
        Cookie cookie = Cookies.getCookie(request, LOCALE_COOKIE_NAME);
        if (cookie != null && cookie.getValue() != null && !cookie.getValue().isEmpty()) return cookie.getValue().toUpperCase();
        return null;
    }

    public static String getLocaleTag() {
        return VaadinSession.getCurrent().getLocale().toLanguageTag();
    }

    public static void saveLocaleTagToCookie(VaadinRequest request, String locale) {
        Cookies.setCookie(request, LOCALE_COOKIE_NAME, locale);
    }

    public static String getText(String name) {
        return getMessage(name);
    }

    public static void init(final ServletContext servletContext) throws Exception {
        logger.info("Init...");
        LocalTime start = LocalTime.now();

        try {
            loadLocaleFile(VMessages.class.getResourceAsStream("messages_en.properties"), "en");
            loadLocaleFile(VMessages.class.getResourceAsStream("messages_ru.properties"), "ru");
            loadLocaleFile(VMessages.class.getResourceAsStream("messages_kz.properties"), "kz");

            loadLocaleFile(servletContext.getResourceAsStream("/WEB-INF/locale/messages_en.properties"), "en");
            loadLocaleFile(servletContext.getResourceAsStream("/WEB-INF/locale/messages_ru.properties"), "ru");
            loadLocaleFile(servletContext.getResourceAsStream("/WEB-INF/locale/messages_kz.properties"), "kz");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("WTF???");
        }

        LocalTime end = LocalTime.now();
        logger.log(Level.INFO, "FINISH [Initializing locales], time: [{0}] seconds [{1} - {2}]", new Object[] {end.toSecondOfDay() - start.toSecondOfDay(), start, end});
    }

    private static void loadLocaleFile(final InputStream inputStream, final String localeTag) {
        PropertiesHelper.doOnPropertiesRead(inputStream, properties -> properties.forEach((name, value) -> createLocale(name.toString(), value.toString(), localeTag)));
    }

    public static String getMessage(final String name) {
        return getMessageRecurse(name, name, getLocale());
    }

    private static String getMessageRecurse(final String aggregate, final String name, final String localeTag) {
        String message = locales.get(localeTag).get(name);
        if (message != null) return message;

        message = locales.get(localeTag).get(name.toLowerCase());
        if (message != null) return message;

        if (aggregate.contains(".")) return getMessageRecurse(aggregate.substring(aggregate.indexOf(".") + 1), name, localeTag);
        else return name;
    }

    private static void createLocale(String name, String value, String localeTag) {
        locales.get(localeTag).put(name, value);
    }

    private static String getLocale() {
        return "ru";
    }

    public static Locale locale() {
        return VaadinSession.getCurrent().getLocale();
    }
}
