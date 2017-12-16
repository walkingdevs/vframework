package brains.vframework.event.api;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface ItemEventListener<ITEM> {

    void onEvent(final EntityItem<ITEM> event);
}
