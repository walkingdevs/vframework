package brains.vframework.presenter;

import brains.vframework.VMessages;
import brains.vframework.annotation.AdditionalProperty;
import brains.vframework.annotation.Property;
import brains.vframework.event.api.EventListener;
import brains.vframework.helpers.Notifications;
import brains.vframework.presenter.api.ContainerBacked;
import brains.vframework.view.BasicListView;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityItem;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.event.source.ItemSelectedEventSource;
import brains.vframework.presenter.api.ViewPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicListPresenter<ITEM, VIEW extends BasicListView> implements ContainerBacked<EntityContainer<ITEM>>, ItemSelectedEventSource<ITEM>, ViewPresenter<VIEW>, Serializable {

    public VIEW view;
    private EntityContainer<ITEM> container;

    private final List<String> visibleColumns = new ArrayList<>();
    private final List<String> additionalVisibleColumns = new ArrayList<>();
    private boolean additionalColumnsVisible = false;
    private boolean defaultTableColumnsDefined = false;

    private List<ItemEventListener<ITEM>> itemSelectedEventListeners = new ArrayList<>();

    @Override
    public void view(final VIEW view) {
        this.view = view;

        // Table
        view.table.addValueChangeListener(event -> {
            Object itemId = selectedItemId();
            if (itemId != null) notifyItemSelectedEventListeners(container.getItem(itemId));
        });

        // Buttons
        view.firstBtn.addClickListener(event -> {
            view.table.setCurrentPage(0);
            setPageIndex(1);
        });

        view.prevBtn.addClickListener(event -> {
            view.table.previousPage();
            setPageIndex(view.table.getCurrentPage());
        });

        view.nextBtn.addClickListener(event -> {
            view.table.nextPage();
            setPageIndex(view.table.getCurrentPage());
        });

        view.lastBtn.addClickListener(event1 -> {
            view.table.setCurrentPage(view.table.getTotalAmountOfPages());
            setPageIndex(view.table.getCurrentPage());
        });

        view.toggleAdditionalColumnsBtn.addClickListener(event -> {
            if (additionalColumnsVisible) {
                view.table.setVisibleColumns(visibleColumns.toArray());
            } else {
                view.table.setVisibleColumns(additionalVisibleColumns.toArray());
            }
            additionalColumnsVisible = !additionalColumnsVisible;
            additionalVisibleColumns.forEach(s -> view.table.setColumnHeader(s, VMessages.getText(s)));
        });
    }

    private void notifyItemSelectedEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemSelectedEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void container(final EntityContainer<ITEM> container) {
        this.container = container;

        if (view.caption.getValue().isEmpty()) {
            view.caption.setValue(VMessages.getText(container.getEntityClass().getSimpleName()));
        }

        view.table.setContainerDataSource(container);
        view.pageCount.setCaption(String.valueOf(view.table.getTotalAmountOfPages()));

        visibleColumns.addAll(Property.Prioritized.readables(container.getEntityClass()));

        additionalVisibleColumns.addAll(visibleColumns);
        additionalVisibleColumns.addAll(AdditionalProperty.Reflect.names(container.getEntityClass()));

        if (!defaultTableColumnsDefined) {
            view.table.setVisibleColumns(visibleColumns.toArray());
            defaultTableColumnsDefined = true;
        }

        // Table Locale
        additionalVisibleColumns.forEach(s -> view.table.setColumnHeader(s, VMessages.getText(s)));

        selectFirstRow();

        view.total.setCaption("Всего: " + String.valueOf(container.size()));

        container.addItemSetChangeListener(itemSetChangeEvent -> {
            view.total.setCaption("Всего: " + String.valueOf(container.size()));
        });
    }

    protected void selectFirstRow() {
        if (view.table.size() == 0) return;
        view.table.select(view.table.firstItemId());
    }

    public void doIfSelected(EventListener<EntityItem<ITEM>> listener) {
        EntityItem<ITEM> selectedItem = selectedItem();
        if (selectedItem != null) {
            listener.onEvent(selectedItem);
        } else {
            Notifications.warning(VMessages.getText("item_not_selected"));
        }
    }

    public void doIfAnySelected(EventListener<List<EntityItem<ITEM>>> listener) {
        List<EntityItem<ITEM>> selectedItems = selectedItems();
        if (selectedItems == null || selectedItems.size() == 0) {
            Notifications.warning(VMessages.getText("item_not_selected"));
        } else {
            listener.onEvent(selectedItems);
        }
    }

    public List<EntityItem<ITEM>> selectedItems() {
        return selectedItemIds().stream().map(container::getItem).collect(Collectors.toList());
    }

    public Set<Object> selectedItemIds() {
        // TODO: Casting, AAA!!! Reimplement fucking Vaadin
        return (Set<Object>) view.table.getValue();
    }

    public Object selectedItemId() {
        if (selectedItemIds().size() == 1) return selectedItemIds().iterator().next();
        return null;
    }

    @Override
    public VIEW view() {
        return view;
    }

    @Override
    public void onReload() {
        page(0);
        container().refresh();
        view.table.refreshRowCache();
        view.pageCount.setCaption(String.valueOf(view.table.getTotalAmountOfPages()));
        view.total.setCaption("Всего: " + String.valueOf(container.size()));
    }

    private void page(final int pageNumber) {
        view.table.setCurrentPage(pageNumber);
        setPageIndex(view.table.getCurrentPage());
    }

    private void setPageIndex(int index) {
        view.pageIndex.setCaption("стр. " + index);
    }

    @Override
    public EntityContainer<ITEM> container() {
        return container;
    }

    public EntityItem<ITEM> selectedItem() {
        return container.getItem(selectedItemId());
    }

    @Override
    public void addItemSelectedEventListener(final ItemEventListener<ITEM> listener) {
        itemSelectedEventListeners.add(listener);
    }

    @Override
    public void removeItemSelectedEventListener(final ItemEventListener<ITEM> listener) {
        itemSelectedEventListeners.remove(listener);
    }

    public void selectLastItem() {
        view.table.setValue(null);
        view.table.setCurrentPage(view.table.getTotalAmountOfPages());
        view.table.select(view.table.lastItemId());
    }

    public void selectItem(final EntityItem<ITEM> item) {
        view.table.setValue(null);
        view.table.select(item.getItemId());
    }
}
