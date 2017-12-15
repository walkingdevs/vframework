package brains.vframework.event.source;

import brains.vframework.event.api.ItemListEventListener;

public interface ItemListSelectedEventSource<ITEM> {

    void addItemListSelectedEventListener(final ItemListEventListener<ITEM> listener);
    void removeItemListSelectedEventListener(final ItemListEventListener<ITEM> listener);
}
