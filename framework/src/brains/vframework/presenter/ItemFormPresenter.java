package brains.vframework.presenter;

import brains.vframework.VMessages;
import brains.vframework.annotation.Property;
import brains.vframework.component.Dialog;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.event.source.ItemCreatedEventSource;
import brains.vframework.event.source.ItemSavedEventSource;
import brains.vframework.helpers.Beans;
import brains.vframework.helpers.Notifications;
import brains.vframework.presenter.api.*;
import brains.vframework.presenter.property.factory.DefaultBasicPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.DefaultListPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.DefaultReferencePropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.BasicPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.ListPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.ReferencePropertyPresenterFactory;
import brains.vframework.spi.ItemAuditor;
import brains.vframework.utils.ItemPropertyBinder;
import brains.vframework.view.CrudView;
import brains.vframework.view.ItemFormView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemFormPresenter<ITEM> implements ItemBacked<ITEM>, Presenter<ItemFormView>, ItemCreatedEventSource<ITEM>, ItemSavedEventSource<ITEM>, Serializable {

    private final List<ItemEventListener<ITEM>> itemSavedEventListeners = new ArrayList<>();
    private final List<ItemEventListener<ITEM>> itemCreatedEventListeners = new ArrayList<>();

    private ItemFormView view;
    private EntityItem<ITEM> item;

    private final ItemPropertyBinder itemPropertyBinder = new ItemPropertyBinder();
    private final BasicPropertyPresenterFactory basicPropertyPresenterFactory = new DefaultBasicPropertyPresenterFactory();
    private final ListPropertyPresenterFactory listPropertyPresenterFactory = new DefaultListPropertyPresenterFactory();
    private final ReferencePropertyPresenterFactory referencePropertyPresenterFactory = new DefaultReferencePropertyPresenterFactory();

    private boolean builded = false;

    private static final Set<ItemAuditor> itemAuditors = Beans.getBeans(ItemAuditor.class);

    @Override
    public void view(final ItemFormView view) {
        this.view = view;

        view.saveBtn.addClickListener(clickEvent -> {
            try {
                save();
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.error(e.getLocalizedMessage());
            }
        });
        view.saveAndCloseBtn.addClickListener(clickEvent -> {
            try {
                save();
                view.close();
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.error(e.getLocalizedMessage());
            }
        });

        view.saveBottomBtn.addClickListener(clickEvent -> view.saveBtn.click());
        view.saveAndCloseBottomBtn.addClickListener(clickEvent -> view.saveAndCloseBtn.click());
        view.getWindow().addWindowCloseListener(windowCloseEvent -> {
            if (itemPropertyBinder.isAnyPropertyModified()) {
                Dialog dialog = new Dialog(new Label(VMessages.getText("not_saved_save?")))
                        .addAction(VMessages.getText("close"), () -> {
                            view.close();
                            itemPropertyBinder.discardAll();
                        })
                        .addAction(VMessages.getText("save"), () -> {
                            try {
                                save();
                                view.close();
                            } catch (Exception e) {
                                onReload();
                                Notifications.error(e.getLocalizedMessage());
                            }
                        });
                dialog.width("400px");
                dialog.height("100px");
                dialog.caption(VMessages.getText("confirm"));
                dialog.getBottomToolbar().removeComponent(dialog.getCloseBtn());
                dialog.show();
                windowCloseEvent.setClose(false);
            }
        });
    }

    @Override
    public void item(final EntityItem<ITEM> item) {
        this.item = item;

        item.setBuffered(true);

        if (!builded) {
            build(item);
        }

        itemPropertyBinder.item(item);

        if (item.isPersistent()) {
            view.title.setValue(VMessages.getText("update") + ": <strong>[" + item.getEntity() + "]</strong>");
        } else {
            view.title.setValue(VMessages.getText("create") + ": <strong>[" + VMessages.getText(item.getEntity().getClass().getSimpleName()) + "]</strong>");
        }
    }

    private void build(final EntityItem<ITEM> item) {
        Property.Prioritized.fields(Property.Writables.ofClass(item.getEntity().getClass())).forEach(tuple -> {
            if (Property.Types.isBasic(tuple.one)) {
                PropertyPresenter<?> propertyPresenter = basicPropertyPresenterFactory.create(item.getEntity().getClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                view.fieldLayout.addComponent(propertyPresenter.view());
            } else if (Property.Types.isReference(tuple.one)) {
                ReferencePropertyPresenter<?, ?> propertyPresenter = referencePropertyPresenterFactory.create(item.getEntity().getClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                view.fieldLayout.addComponent(propertyPresenter.view());
            } else {
                ListPropertyPresenter<?, ?> propertyPresenter = listPropertyPresenterFactory.create(item.getEntity().getClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                if (CrudView.class.isAssignableFrom(propertyPresenter.view().getClass())) {
                    Label caption = ((CrudView) propertyPresenter.view()).getListView().caption;
                    ((CrudView) propertyPresenter.view()).getListView().topToolbar.removeComponent(caption);
                }
                view.tabSheet.addTab(propertyPresenter.view(), VMessages.getText(tuple.one.getName()));
            }
        });
        if (view.tabSheet.getComponentCount() < 1) {
            view.content.removeComponent(view.tabSheet);
            view.content.setSplitPosition(0, Sizeable.Unit.PIXELS, true);

            view.getWindow().setWidth("500px");
        }
        builded = true;
    }



    private void save() throws Exception {
        for (final ItemAuditor itemAuditor : itemAuditors) {
            itemAuditor.preUpdate(item.getEntity());
        }
        item.commit();
        if (!item.isPersistent()) {
            for (final ItemAuditor itemAuditor : itemAuditors) {
                itemAuditor.prePersist(item.getEntity());
            }

            Object o = item.getContainer().addEntity(item.getEntity());
            item(item.getContainer().getItem(o));
            notifyItemCreatedEventListeners(item);
            Notifications.message(VMessages.getText("item_created"));
        } else {
            notifyItemSavedEventListeners(item);
            Notifications.message(VMessages.getText("item_saved"));
        }
        onReload();
    }

    @Override
    public EntityItem<ITEM> item() {
        return item;
    }

    @Override
    public ItemFormView view() {
        return view;
    }

    @Override
    public void onReload() {
        item.refresh();
        itemPropertyBinder.item(item);
    }

    private void notifyItemSavedEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemSavedEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void addItemSavedEventListener(final ItemEventListener<ITEM> listener) {
        itemSavedEventListeners.add(listener);
    }

    @Override
    public void removeItemSavedEventListener(final ItemEventListener<ITEM> listener) {
        itemSavedEventListeners.remove(listener);
    }

    private void notifyItemCreatedEventListeners(final EntityItem<ITEM> item) {
        for (ItemEventListener<ITEM> listener : itemCreatedEventListeners) {
            listener.onEvent(item);
        }
    }

    @Override
    public void addItemCreatedEventListener(final ItemEventListener<ITEM> listener) {
        itemCreatedEventListeners.add(listener);
    }

    @Override
    public void removeItemCreatedEventListener(final ItemEventListener<ITEM> listener) {
        itemCreatedEventListeners.remove(listener);
    }

    public PropertyPresenter<?> getPropertyPresenterFor(final String propertyId) {
        return itemPropertyBinder.getMap().get(propertyId);
    }
}
