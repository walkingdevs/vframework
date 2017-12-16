package brains.vframework.event;

import com.vaadin.addon.jpacontainer.EntityItem;

public class ItemDeletingEvent<ITEM> {

    private final EntityItem<ITEM> item;
    private boolean delete = true;

    public ItemDeletingEvent(final EntityItem<ITEM> item) {
        this.item = item;
    }

    public EntityItem<ITEM> getItem() {
        return item;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(final boolean delete) {
        this.delete = delete;
    }
}
