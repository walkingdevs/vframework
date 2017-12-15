package brains.vframework.presenter.api;

import com.vaadin.data.Container;

public interface ContainerBacked<CONTAINER extends Container> {

    CONTAINER container();
    void container(CONTAINER container);
}
