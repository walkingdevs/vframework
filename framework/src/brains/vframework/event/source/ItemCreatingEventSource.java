package brains.vframework.event.source;

import brains.vframework.event.api.ItemEventListener;

public interface ItemCreatingEventSource<ITEM> {

    void addItemCreatingEventListener(final ItemEventListener<ITEM> listener);
    void removeItemCreatingEventListener(final ItemEventListener<ITEM> listener);
}
