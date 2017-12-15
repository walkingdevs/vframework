package brains.vframework.presenter;

import brains.vframework.event.source.ItemListSelectedEventSource;
import com.vaadin.addon.jpacontainer.EntityItem;
import brains.vframework.event.api.ItemListEventListener;
import brains.vframework.view.ItemSelectorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemSelectorPresenter<ITEM> extends CrudPresenter<ITEM, ItemSelectorView> implements ItemListSelectedEventSource<ITEM>, Serializable {

    private final List<ItemListEventListener<ITEM>> itemListEventListeners = new ArrayList<>();

    @Override
    public void view(final ItemSelectorView view) {
        super.view(view);

        view.getSelectBtn().addClickListener(event -> {
            listPresenter().doIfAnySelected(item -> notifyItemListEventListeners(listPresenter().selectedItems()));
            view.getWindow().close();
        });
    }

    private void notifyItemListEventListeners(final List<EntityItem<ITEM>> items) {
        for (ItemListEventListener<ITEM> listener : itemListEventListeners) {
            listener.onEvent(items);
        }
    }

    @Override
    public void addItemListSelectedEventListener(final ItemListEventListener<ITEM> listener) {
        itemListEventListeners.add(listener);
    }

    @Override
    public void removeItemListSelectedEventListener(final ItemListEventListener<ITEM> listener) {
        itemListEventListeners.remove(listener);
    }
}