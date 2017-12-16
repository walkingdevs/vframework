package brains.vframework.presenter.api;

public interface Presenter<VIEW> {

    VIEW view();
    void view(VIEW view);

    void onReload();
}
