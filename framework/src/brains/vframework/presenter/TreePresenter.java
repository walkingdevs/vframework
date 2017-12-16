package brains.vframework.presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.event.source.ItemSelectedEventSource;
import brains.vframework.presenter.api.ContainerBacked;
import brains.vframework.presenter.api.ViewPresenter;
import brains.vframework.view.TreeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreePresenter<ITEM> implements ContainerBacked<JPAContainer<ITEM>>, ViewPresenter<TreeView>, ItemSelectedEventSource<ITEM>, Serializable {

    private final List<ItemEventListener<ITEM>> itemSelectedEventListeners = new ArrayList<>();

    private TreeView view;
    private JPAContainer<ITEM> container;

    @Override
    public void container(final JPAContainer<ITEM> container) {
        this.container = container;

        view.tree.setContainerDataSource(container);
        container.rootItemIds().forEach(view.tree::expandItemsRecursively);
        view.tree.addValueChangeListener(valueChangeEvent -> {
            if (view.tree.getValue() != null) {
                EntityItem<ITEM> item = container.getItem(view.tree.getValue());
                notifyItemSelectedEventListeners(item);
            }
        });
    }

    @Override
    public JPAContainer<ITEM> container() {
        return container;
    }

    @Override
    public TreeView view() {
        return view;
    }

    @Override
    public void view(final TreeView view) {
        this.view = view;
    }

    @Override
    public void onReload() {
        container.refresh();
    }

    private void notifyItemSelectedEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemSelectedEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void addItemSelectedEventListener(final ItemEventListener<ITEM> listener) {
        itemSelectedEventListeners.add(listener);
    }

    @Override
    public void removeItemSelectedEventListener(final ItemEventListener<ITEM> listener) {
        itemSelectedEventListeners.remove(listener);
    }
}