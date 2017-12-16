package brains.vframework.event.source;

import brains.vframework.event.api.ItemEventListener;

public interface ItemDeletedEventSource<ITEM> {

    void addItemDeletedEventListener(final ItemEventListener<ITEM> listener);
    void removeItemDeletedEventListener(final ItemEventListener<ITEM> listener);
}
