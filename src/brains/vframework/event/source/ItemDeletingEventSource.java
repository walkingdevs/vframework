package brains.vframework.event.source;

import brains.vframework.event.api.ItemDeletingEventListener;

public interface ItemDeletingEventSource<ITEM> {

    void addItemDeletingEventListener(final ItemDeletingEventListener<ITEM> listener);
    void removeItemDeletingEventListener(final ItemDeletingEventListener<ITEM> listener);
}
