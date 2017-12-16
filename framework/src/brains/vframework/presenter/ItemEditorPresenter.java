package brains.vframework.presenter;

import brains.vframework.VMessages;
import brains.vframework.annotation.Property;
import brains.vframework.event.source.ItemSavedEventSource;
import brains.vframework.helpers.Beans;
import brains.vframework.helpers.Notifications;
import brains.vframework.presenter.api.PropertyPresenter;
import brains.vframework.presenter.api.ReferencePropertyPresenter;
import brains.vframework.presenter.property.factory.DefaultBasicPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.DefaultReferencePropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.BasicPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.ReferencePropertyPresenterFactory;
import brains.vframework.utils.ItemPropertyBinder;
import brains.vframework.view.ItemEditorView;
import com.vaadin.addon.jpacontainer.EntityItem;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.presenter.api.ItemBacked;
import brains.vframework.presenter.api.ViewPresenter;
import brains.vframework.spi.ItemAuditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemEditorPresenter<ITEM> implements ItemBacked<ITEM>, ViewPresenter<ItemEditorView>, ItemSavedEventSource<ITEM>, Serializable {

    private ItemEditorView view;
    private EntityItem<ITEM> item;

    private boolean builded = false;
    private final ItemPropertyBinder itemPropertyBinder = new ItemPropertyBinder();
    private final BasicPropertyPresenterFactory basicPropertyPresenterFactory = new DefaultBasicPropertyPresenterFactory();
    private final ReferencePropertyPresenterFactory referencePropertyPresenterFactory = new DefaultReferencePropertyPresenterFactory();

    private final List<ItemEventListener<ITEM>> itemSavedEventListeners = new ArrayList<>();

    private static final Set<ItemAuditor> itemAuditors = Beans.getBeans(ItemAuditor.class);

    @Override
    public void view(final ItemEditorView view) {
        this.view = view;

        view.saveBtn.addClickListener(clickEvent -> {
            try {
                save();
            } catch (Exception e) {
                e.printStackTrace();
                Notifications.error(e.getLocalizedMessage());
            }
        });
        view.saveTopBtn.addClickListener(clickEvent -> view.saveBtn.click());
    }

    @Override
    public void item(final EntityItem<ITEM> item) {
        this.item = item;

        item.setBuffered(true);

        if (!builded) {
            build(item);
        }
        itemPropertyBinder.item(item);

        view.title.setValue(item.getEntity().toString());
    }

    private void build(final EntityItem<ITEM> item) {
        Property.Prioritized.fields(Property.Writables.ofClass(item.getEntity().getClass())).forEach(tuple -> {
            if (Property.Types.isBasic(tuple.one)) {
                PropertyPresenter<?> propertyPresenter = basicPropertyPresenterFactory.create(item.getContainer().getEntityClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                view.fieldLayout.addComponent(propertyPresenter.view());
            } else if (Property.Types.isReference(tuple.one)) {
                ReferencePropertyPresenter<?, ?> propertyPresenter = referencePropertyPresenterFactory.create(item.getEntity().getClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                view.fieldLayout.addComponent(propertyPresenter.view());
            }
        });
        builded = true;
    }

    private void save() throws Exception {
        item.commit();

        for (final ItemAuditor itemAuditor : itemAuditors) {
            itemAuditor.preUpdate(item.getEntity());
        }

        notifyItemSavedEventListeners(item);
        Notifications.message(VMessages.getText("item_saved"));

        onReload();
    }

    @Override
    public EntityItem<ITEM> item() {
        return item;
    }

    @Override
    public ItemEditorView view() {
        return view;
    }

    @Override
    public void onReload() {
        item.refresh();
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
}
