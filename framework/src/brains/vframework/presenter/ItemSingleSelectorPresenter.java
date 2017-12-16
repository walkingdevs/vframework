package brains.vframework.presenter;

import com.vaadin.addon.jpacontainer.EntityItem;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.event.source.ItemSelectedEventSource;
import brains.vframework.view.ItemSelectorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemSingleSelectorPresenter<ITEM> extends CrudPresenter<ITEM, ItemSelectorView> implements ItemSelectedEventSource<ITEM>, Serializable {

    private final List<ItemEventListener<ITEM>> itemSelectedEventListeners = new ArrayList<>();

    @Override
    public void view(final ItemSelectorView view) {
        super.view(view);

        view.getSelectBtn().addClickListener(event -> {
            listPresenter().doIfSelected(this::notifyItemSelectedEventListeners);
            view.close();
        });
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