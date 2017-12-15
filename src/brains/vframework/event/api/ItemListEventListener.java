package brains.vframework.event.api;

import com.vaadin.addon.jpacontainer.EntityItem;

import java.util.List;

public interface ItemListEventListener<ITEM> {

    void onEvent(final List<EntityItem<ITEM>> event);
}
