package brains.vframework.presenter.api;

import brains.vframework.view.api.View;

public abstract class AbstractViewPresenter<VIEW extends View> implements ViewPresenter<VIEW> {
    @Override
    public VIEW view() {
        return view;
    }

    @Override
    public void onReload() {
    }

    protected VIEW view;
}