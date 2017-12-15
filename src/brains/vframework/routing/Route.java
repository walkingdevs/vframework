package brains.vframework.routing;

import brains.vframework.VContext;
import brains.vframework.presenter.api.RouteViewPresenter;
import com.vaadin.ui.UI;

import java.io.Serializable;

public class Route implements Serializable {
    public static void to(final Class<? extends RouteViewPresenter> presenter) {
        UI.getCurrent().getNavigator().navigateTo(VContext.getPresenterNameBy(presenter));
    }

    public static void toCrud(final Class<?> _class) {
        UI.getCurrent().getNavigator().navigateTo("EntityExplorer/" + _class.getName());
    }

    public static void toMasterDetail(final Class<?> _class) {
        UI.getCurrent().getNavigator().navigateTo("MasterDetailExplorer/" + _class.getName());
    }
}