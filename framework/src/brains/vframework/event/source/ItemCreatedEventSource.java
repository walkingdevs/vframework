package brains.vframework.event.source;

import brains.vframework.event.api.ItemEventListener;

public interface ItemCreatedEventSource<ITEM> {

    void addItemCreatedEventListener(final ItemEventListener<ITEM> listener);
    void removeItemCreatedEventListener(final ItemEventListener<ITEM> listener);
}
