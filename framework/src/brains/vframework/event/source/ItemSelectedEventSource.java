package brains.vframework.event.source;

import brains.vframework.event.api.ItemEventListener;

public interface ItemSelectedEventSource<ITEM> {

    void addItemSelectedEventListener(final ItemEventListener<ITEM> listener);
    void removeItemSelectedEventListener(final ItemEventListener<ITEM> listener);
}
