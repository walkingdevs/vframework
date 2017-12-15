package brains.vframework.presenter.route;

import brains.vframework.exception.RouteParseException;
import brains.vframework.VContainers;
import brains.vframework.presenter.MasterDetailPresenter;
import brains.vframework.presenter.api.RouteViewPresenter;
import brains.vframework.view.MasterDetailView;
import brains.vframework.view.route.MasterDetailExplorerView;

import java.io.Serializable;

public class MasterDetailExplorerPresenter implements RouteViewPresenter<MasterDetailExplorerView>, Serializable {
    private MasterDetailExplorerView view;
    private MasterDetailPresenter<?, MasterDetailView> masterDetailPresenter = new MasterDetailPresenter<>();

    @Override
    public MasterDetailExplorerView view() {
        return view;
    }

    @Override
    public void view(final MasterDetailExplorerView view) {
        this.view = view;

        masterDetailPresenter.view(view.masterDetailView);
    }

    @Override
    public void onRequest(final String request) throws RouteParseException {
        if (masterDetailPresenter.container() != null) return;
        try {
            masterDetailPresenter.container(VContainers.createContainerUntyped(Class.forName(request)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReload() {
    }
}
