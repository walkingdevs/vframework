package brains.vframework.presenter.api;

import brains.vframework.exception.RouteParseException;
import brains.vframework.view.api.RouteView;

public interface RouteViewPresenter<VIEW extends RouteView> extends Presenter<VIEW> {

    String SUFFIX = "Presenter";

    void onRequest(final String request) throws RouteParseException;
}
