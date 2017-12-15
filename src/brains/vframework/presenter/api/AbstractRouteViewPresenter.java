package brains.vframework.presenter.api;

import brains.vframework.exception.RouteParseException;
import brains.vframework.view.api.RouteView;

public abstract class AbstractRouteViewPresenter<VIEW extends RouteView> implements RouteViewPresenter<VIEW> {
    @Override
    public void onRequest(String request) throws RouteParseException {
    }

    @Override
    public VIEW view() {
        return view;
    }

    @Override
    public void onReload() {
    }

    protected VIEW view;
}