package brains.vframework.presenter.property;

import brains.vframework.presenter.ManyToManyPresenter;
import brains.vframework.presenter.api.ListPropertyPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.EntityItemProperty;
import com.vaadin.data.Validator;
import brains.vframework.view.property.ManyToManyView;

import java.io.Serializable;

public class ManyToManyPropertyPresenter<ITEM, REFERENCE, PIVOT> extends ManyToManyPresenter<ITEM, REFERENCE, PIVOT, ManyToManyView> implements ListPropertyPresenter<PIVOT, ManyToManyView>, Serializable {

    private EntityItemProperty propertyDataSource;

    public ManyToManyPropertyPresenter(final String listProperty, final String mappedByProperty, final String pivotProperty, final Class<REFERENCE> referenceClass) {
        super(listProperty, mappedByProperty, pivotProperty, referenceClass);
    }

    @Override
    public EntityItemProperty propertyDataSource() {
        return propertyDataSource;
    }

    @Override
    public void propertyDataSource(final EntityItemProperty propertyDataSource) {
        this.propertyDataSource = propertyDataSource;
        item((EntityItem<ITEM>) propertyDataSource.getItem());
    }

    @Override
    public void addValidator(final Validator validator) {
    }

    @Override
    public void removeValidator(final Validator validator) {
    }

    @Override
    public boolean isModified() {
        return unsaved.size() > 0;
    }

    @Override
    public void discard() {
        for (EntityItem<PIVOT> item : unsaved) {
            container().removeItem(item.getItemId());
        }
    }
}
