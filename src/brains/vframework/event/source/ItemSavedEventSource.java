package brains.vframework.event.source;

import brains.vframework.event.api.ItemEventListener;

public interface ItemSavedEventSource<ITEM> {

    void addItemSavedEventListener(final ItemEventListener<ITEM> listener);
    void removeItemSavedEventListener(final ItemEventListener<ITEM> listener);
}
