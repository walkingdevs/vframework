package brains.vframework;

import brains.vframework.view.api.RouteView;
import brains.vframework.annotation.DefaultUI;
import brains.vframework.presenter.api.Presenter;
import brains.vframework.presenter.api.RouteViewPresenter;
import brains.vframework.ui.ITWORKSUI;
import org.reflections.Reflections;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VContext implements Serializable {

    private static final Logger logger = Logger.getLogger(VContext.class.getName());
    private static Reflections reflections;

    private static Pattern uiNamePattern = Pattern.compile("(\\w+)" + VUI.SUFFIX);
    private static Map<String, Class<? extends VUI>> uiTypes = new HashMap<>();
    private static Pattern viewNamePattern = Pattern.compile("(\\w+)" + RouteView.SUFFIX);
    private static Pattern presenterNamePattern = Pattern.compile("(\\w+)" + RouteViewPresenter.SUFFIX);

    private static Map<String, Class<? extends RouteView>> stringViewTypeMap = new HashMap<>();
    private static Map<String, Class<? extends Presenter>> stringPresenterTypeMap = new HashMap<>();

    private static Map<Class<? extends RouteView>, String> viewTypeStringMap = new HashMap<>();
    private static Map<Class<? extends Presenter>, String> PresenterTypeStringMap = new HashMap<>();

    private static EntityManager entityManager;
    private static Class<? extends VUI> defaultUI;
    private static String theme;

    public static void init(final EntityManager entityManager, final String theme) throws Exception {
        VContext.entityManager = entityManager;
        VContext.theme = theme;
        defaultUI = ITWORKSUI.class;

        reflections = new Reflections();

        discoverUIs();
        discoverViews();
        discoverPresenters();
    }

    private static void discoverUIs() throws Exception {
        logger.log(Level.INFO, "Discovering UIs...");
        for (Class<? extends VUI> type : getSubTypesOf(VUI.class)) {
            String uiName = type.getSimpleName();
            uiTypes.put(uiName.toLowerCase(), type);
            logger.log(Level.INFO, "\tRegistered UI [{0}]", uiName);
            if (type.isAnnotationPresent(DefaultUI.class)) {
                defaultUI = type;
                logger.log(Level.INFO, "\t[{0}] is Default UI", uiName);
            }
        }
    }

    public static Class<? extends VUI> getUIType(String uiName) {
        return uiTypes.get(uiName);
    }

    public static  <T> T getBean(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static  <T> Set<T> getBeans(Class<T> type) {
        Set<T> beans = new HashSet<>();
        for (Class<? extends T> subType : reflections.getSubTypesOf(type)) {
            beans.add(getBean(subType));
        }
        return beans;
    }

    public static  <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

    private static void discoverViews() throws Exception {
        logger.log(Level.INFO, "Discovering Views...");
        for (Class<? extends RouteView> type : getSubTypesOf(RouteView.class)) {
            Matcher matcher = viewNamePattern.matcher(type.getSimpleName());
            if (!matcher.matches()) throw new Exception("View name must end with 'View', " + type.getSimpleName());

            String presenterName = matcher.group(1);
            if (stringViewTypeMap.containsKey(presenterName)) {
                logger.log(Level.SEVERE, "Duplicate View name [{0}]", presenterName);
                throw new RuntimeException();
            }

            stringViewTypeMap.put(presenterName, type);
            viewTypeStringMap.put(type, presenterName);

            logger.log(Level.INFO, "\tRegistered View [{0}]", type);
        }
    }

    public static void discoverPresenters() throws Exception {
        logger.log(Level.INFO, "Discovering Presenters...");
        for (Class<? extends RouteViewPresenter> type : getSubTypesOf(RouteViewPresenter.class)) {
            Matcher matcher = presenterNamePattern.matcher(type.getSimpleName());
            if (!matcher.matches()) throw new Exception("Presenter name must end with 'Presenter', " + type.getSimpleName());

            String presenterName = matcher.group(1);
            if (stringPresenterTypeMap.containsKey(presenterName)) throw new Exception("Duplicate Presenter name " + presenterName);

            stringPresenterTypeMap.put(presenterName, type);
            PresenterTypeStringMap.put(type, presenterName);

            logger.log(Level.INFO, "\tRegistered Presenter [{0}]", type);
        }
    }

    public static  <T extends RouteView> Class<T> getViewTypeByPresenterName(final String mvName) {
        return (Class<T>) stringViewTypeMap.get(mvName);
    }

    public static  <V extends RouteView, P extends Presenter<V>> Class<P> getViewModelType(final String presenterName) {
        return (Class<P>) stringPresenterTypeMap.get(presenterName);
    }

    public static String getPresenterNameBy(Class<? extends Presenter> presenterType) {
        return PresenterTypeStringMap.get(presenterType);
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static Class<? extends VUI> getDefaultUI() {
        return defaultUI;
    }

    public static String getTheme() {
        return theme;
    }
}
