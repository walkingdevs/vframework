package brains.vframework.routing;

import brains.vframework.presenter.CrudPresenter;
import brains.vframework.presenter.api.RouteViewPresenter;
import brains.vframework.VContainers;
import brains.vframework.view.DefaultCrudView;

import java.io.Serializable;

public class Windows implements Serializable {
    public static void open(Class<? extends RouteViewPresenter> presenter) {
    }

    public static <ITEM> void openCrud(final Class<ITEM> forClass) {
        CrudPresenter<ITEM, DefaultCrudView> presenter = new CrudPresenter<>();
        presenter.view(new DefaultCrudView());
        presenter.container(VContainers.createContainer(forClass));
        presenter.view().open();
    }
}

