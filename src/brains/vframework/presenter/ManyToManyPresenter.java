package brains.vframework.presenter;

import brains.vframework.VContainers;
import brains.vframework.view.property.ManyToManyView;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Or;
import brains.vframework.VUI;
import brains.vframework.helpers.Reflections;
import brains.vframework.presenter.api.ItemBacked;

import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ManyToManyPresenter<ITEM, REFERENCE, PIVOT, VIEW extends ManyToManyView> extends ListPresenter<PIVOT, VIEW> implements ItemBacked<ITEM>, Serializable {

    private final ItemSelectorPresenter<REFERENCE> itemSelectorPresenter = new ItemSelectorPresenter<>();
    private final Class<REFERENCE> referenceClass;
    private final String mappedByProperty;
    private final String listProperty;
    private boolean autoSave = true;

    protected final List<EntityItem<PIVOT>> unsaved = new ArrayList<>();

    private EntityItem<ITEM> item;

    public ManyToManyPresenter(final String listProperty, final String mappedByProperty, final String pivotProperty, final Class<REFERENCE> referenceClass) {
        this.listProperty = listProperty;
        this.mappedByProperty = mappedByProperty;
        this.referenceClass = referenceClass;

        itemSelectorPresenter.addItemListSelectedEventListener(entityItems -> {
            try {
                for (EntityItem<REFERENCE> referenceEntityItem : entityItems) {
                    EntityItem<PIVOT> pivotEntityItem = createPivotEntityItem();
                    pivotEntityItem.getItemProperty(pivotProperty).setValue(referenceEntityItem.getEntity());
                    addToList(pivotEntityItem);
                    if (autoSave && item().isPersistent()) {
                        setMappedBy(pivotEntityItem, item());
                        pivotEntityItem.commit();
                    } else {
                        unsaved.add(pivotEntityItem);
                    }
                }
                onReload();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void view(final VIEW view) {
        super.view(view);
        itemSelectorPresenter.view(view.itemSelectorView);

        view.add.addClickListener(clickEvent -> itemSelectorPresenter.view().open());
        view.remove.addClickListener(event -> {
            if (view.table.getValue() == null) return;
            for (EntityItem<PIVOT> entityItem : selectedItems()) {
                container().removeItem(entityItem.getItemId());
            }
            view.table.refreshRowCache();
        });
    }

    @Override
    public void container(final EntityContainer<PIVOT> container) {
        super.container(container);
        itemSelectorPresenter.container(VContainers.createContainer(referenceClass));
    }

    @Override
    public VIEW view() {
        return this.view;
    }

    @Override
    public EntityItem<ITEM> item() {
        return item;
    }

    @Override
    public void item(final EntityItem<ITEM> item) {
        this.item = item;

        if (item != null && item.isPersistent()) {
            for (EntityItem<PIVOT> pivotEntityItem : unsaved) {
                setMappedBy(pivotEntityItem, item);
                pivotEntityItem.commit();
            }
            unsaved.clear();
        }

        // Удалить осиротевшие записи. Почему? JPAContainer.
        try {
            VUI.current().getUserTransaction().begin();
            container().getEntityProvider().getEntityManager().
                    createQuery("DELETE FROM " + container().getEntityClass().getSimpleName() + " i WHERE i." + mappedByProperty + " IS NULL").
                    executeUpdate();
            VUI.current().getUserTransaction().commit();
        } catch (NotSupportedException | SystemException | HeuristicRollbackException | RollbackException | HeuristicMixedException e) {
            e.printStackTrace();
            try {
                VUI.current().getUserTransaction().rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }

        container().removeAllContainerFilters();
        container().addContainerFilter(new Or(
                new Compare.Equal(mappedByProperty, item.getEntity()),
                new IsNull(mappedByProperty)));

        view().table.refreshRowCache();
    }

    private void setMappedBy(EntityItem<PIVOT> pivotEntityItem, EntityItem<ITEM> item) {
        pivotEntityItem.getItemProperty(mappedByProperty).setValue(item.getEntity());
    }

    private void addToList(EntityItem<PIVOT> referenceEntityItem) {
        // JPA как тебе не стыдно...
        ((Collection) item.getItemProperty(listProperty).getValue()).add(referenceEntityItem.getEntity());
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
    }

    private EntityItem<PIVOT> createPivotEntityItem() throws IllegalAccessException, InstantiationException {
        Object newPivotItemId = container().addEntity(Reflections.newInstance(container().getEntityClass()));
        return container().getItem(newPivotItemId);
    }
}
