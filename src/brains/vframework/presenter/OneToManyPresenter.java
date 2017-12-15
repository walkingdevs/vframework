package brains.vframework.presenter;

import brains.vframework.VUI;
import brains.vframework.presenter.api.ItemBacked;
import brains.vframework.view.DefaultCrudView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Or;

import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OneToManyPresenter<ITEM, REFERENCE> extends CrudPresenter<REFERENCE, DefaultCrudView> implements ItemBacked<ITEM>, Serializable {

    private EntityItem<ITEM> item;
    private final String mappedByProperty;
    private final String listProperty;
    private boolean autoSave = true;

    protected final List<EntityItem<REFERENCE>> unsaved = new ArrayList<>();

    public OneToManyPresenter(final String listProperty, final String mappedByProperty) {
        this.listProperty = listProperty;
        this.mappedByProperty = mappedByProperty;

        itemFormPresenter().addItemCreatedEventListener(referenceEntityItem -> {
            addToList(referenceEntityItem);
            if (autoSave && item().isPersistent()) {
                setMappedBy(referenceEntityItem, item());
                referenceEntityItem.commit();

                item().commit();
                container().refresh();
                item = item.getContainer().getItem(item.getItemId());
            } else {
                unsaved.add(referenceEntityItem);
            }
        });
    }

    @Override
    public EntityItem<ITEM> item() {
        return item;
    }

    @Override
    public void item(final EntityItem<ITEM> item) {
        this.item = item;

        if (item != null && item.isPersistent()) {
            for (EntityItem<REFERENCE> referenceEntityItem : unsaved) {
                setMappedBy(referenceEntityItem, item);
                referenceEntityItem.commit();
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
            } catch (SystemException fail) {
                fail.printStackTrace();
            }
        }

        container().removeAllContainerFilters();
        container().addContainerFilter(new Or(
                new Compare.Equal(mappedByProperty, item().getEntity()),
                new IsNull(mappedByProperty)));

        view().getListView().table.refreshRowCache();
    }

    private void setMappedBy(EntityItem<REFERENCE> referenceEntityItem, EntityItem<ITEM> item) {
        referenceEntityItem.getItemProperty(mappedByProperty).setValue(item().getEntity());
    }

    private void addToList(EntityItem<REFERENCE> referenceEntityItem) {
        // JPA как тебе не стыдно...
        ((Collection) item().getItemProperty(listProperty).getValue()).add(referenceEntityItem.getEntity());
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
    }
}
