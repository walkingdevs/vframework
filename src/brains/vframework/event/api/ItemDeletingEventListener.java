package brains.vframework.event.api;

import brains.vframework.event.ItemDeletingEvent;

public interface ItemDeletingEventListener<ITEM> {

    void onEvent(final ItemDeletingEvent<ITEM> event);
}
