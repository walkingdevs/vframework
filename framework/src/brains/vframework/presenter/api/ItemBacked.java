package brains.vframework.presenter.api;

import com.vaadin.addon.jpacontainer.EntityItem;

public interface ItemBacked<ITEM> {

    EntityItem<ITEM> item();
    void item(final EntityItem<ITEM> item);
}
