package brains.vframework.presenter;

import brains.vframework.VContext;
import brains.vframework.VMessages;
import brains.vframework.event.ItemDeletingEvent;
import brains.vframework.event.api.ItemDeletingEventListener;
import brains.vframework.event.source.ItemCreatingEventSource;
import brains.vframework.helpers.Notifications;
import brains.vframework.presenter.api.ContainerBacked;
import brains.vframework.view.ListView;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityItem;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.event.source.ItemDeletedEventSource;
import brains.vframework.event.source.ItemDeletingEventSource;
import brains.vframework.presenter.api.ViewPresenter;
import brains.vframework.view.CrudView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrudPresenter<ITEM, VIEW extends CrudView> implements ContainerBacked<EntityContainer<ITEM>>, ItemCreatingEventSource<ITEM>, ItemDeletedEventSource<ITEM>, ItemDeletingEventSource<ITEM>, ViewPresenter<VIEW>, Serializable {

    private final List<ItemEventListener<ITEM>> itemCreatingEventListeners = new ArrayList<>();
    private final List<ItemEventListener<ITEM>> itemDeletedEventListeners = new ArrayList<>();
    private final List<ItemDeletingEventListener<ITEM>> itemDeletingEventListeners = new ArrayList<>();

    private VIEW view;
    private EntityContainer<ITEM> container;

    private ItemFormPresenter<ITEM> itemFormPresenter = new ItemFormPresenter<>();
    private ItemEditorPresenter<ITEM> itemEditorPresenter = new ItemEditorPresenter<>();
    private ListPresenter<ITEM, ListView> listPresenter = new ListPresenter<>();

    public CrudPresenter() {
        listPresenter.addItemSelectedEventListener(itemEditorPresenter::item);
        listPresenter.addContainerRefreshedEventListener(() -> {
            if (listPresenter.selectedItem() != null) itemEditorPresenter.item(listPresenter.selectedItem());
        });

        itemFormPresenter.addItemSavedEventListener(o -> {
            listPresenter.onReload();
            listPresenter.selectItem(o);
        });
        itemFormPresenter.addItemCreatedEventListener(o -> {
            listPresenter.onReload();
            listPresenter.selectLastItem();
        });

        itemEditorPresenter.addItemSavedEventListener(event -> {
            container.refreshItem(event);
        });
    }

    @Override
    public void view(final VIEW view) {
        this.view = view;

        itemFormPresenter.view(view.getItemFormView());
        itemEditorPresenter.view(view.getItemEditorView());
        listPresenter.view(view.getListView());

        view.getNewBtn().addClickListener(event -> {
            EntityItem<ITEM> newItem = container.createEntityItem(VContext.getBean(container.getEntityClass()));
            notifyItemCreatingEventListeners(newItem);
            itemFormPresenter.item(newItem);
            // TODO вызов не должен зависеть от itemFormPresenter.item(newItem);
            view.getItemFormView().open();
        });
        view.getEditBtn().addClickListener(event -> listPresenter.doIfSelected(o -> {
            itemFormPresenter.item(listPresenter.selectedItem());
            view.getItemFormView().open();
        }));
        view.getDeleteBtn().addClickListener(clickEvent -> listPresenter.doIfSelected(item -> {
            final ItemDeletingEvent<ITEM> itemItemDeletingEvent = new ItemDeletingEvent<>(item);
            notifyItemDeletingEventListeners(itemItemDeletingEvent);

            if (itemItemDeletingEvent.isDelete()) {
                try {
                    container.removeItem(item.getItemId());
                    onReload();
                    notifyItemDeletedEventListeners(item);
                    Notifications.message(VMessages.getText("item_deleted") + ": " + item.getEntity());
                } catch (Exception e) {
                    Notifications.error(e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }));
        view.getToggleShowBtn().addClickListener(clickEvent -> {
            if (itemEditorPresenter.item() == null) return;
            view.toggleEditor(false);
        });
    }

    public ListPresenter<ITEM, ListView> listPresenter() {
        return listPresenter;
    }

    public void listPresenter(final ListPresenter<ITEM, ListView> listPresenter) {
        this.listPresenter = listPresenter;
    }

    public ItemEditorPresenter<ITEM> itemEditorPresenter() {
        return itemEditorPresenter;
    }

    public void itemEditorPresenter(final ItemEditorPresenter<ITEM> itemEditorPresenter) {
        this.itemEditorPresenter = itemEditorPresenter;
    }

    public ItemFormPresenter<ITEM> itemFormPresenter() {
        return itemFormPresenter;
    }

    public void itemFormPresenter(final ItemFormPresenter<ITEM> itemFormPresenter) {
        this.itemFormPresenter = itemFormPresenter;
    }

    @Override
    public EntityContainer<ITEM> container() {
        return container;
    }

    @Override
    public void container(final EntityContainer<ITEM> container) {
        this.container = container;

        listPresenter.container(container);
    }

    @Override
    public VIEW view() {
        return view;
    }

    @Override
    public void onReload() {
        listPresenter.onReload();
    }

    protected void notifyItemCreatingEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemCreatingEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void addItemCreatingEventListener(final ItemEventListener<ITEM> listener) {
        itemCreatingEventListeners.add(listener);
    }

    @Override
    public void removeItemCreatingEventListener(final ItemEventListener<ITEM> listener) {
        itemCreatingEventListeners.remove(listener);
    }

    protected void notifyItemDeletedEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemDeletedEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void addItemDeletedEventListener(final ItemEventListener<ITEM> listener) {
        itemDeletedEventListeners.add(listener);
    }

    @Override
    public void removeItemDeletedEventListener(final ItemEventListener<ITEM> listener) {
        itemDeletedEventListeners.remove(listener);
    }

    @Override
    public void addItemDeletingEventListener(ItemDeletingEventListener<ITEM> listener) {
        itemDeletingEventListeners.add(listener);
    }

    @Override
    public void removeItemDeletingEventListener(ItemDeletingEventListener<ITEM> listener) {
        itemDeletingEventListeners.remove(listener);
    }

    protected void notifyItemDeletingEventListeners(final ItemDeletingEvent<ITEM> event) {
        for (ItemDeletingEventListener<ITEM> listener : itemDeletingEventListeners) {
            listener.onEvent(event);
        }
    }
}
