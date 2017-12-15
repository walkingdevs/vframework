package brains.vframework.presenter.property;

import brains.vframework.presenter.OneToManyPresenter;
import brains.vframework.presenter.api.ListPropertyPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.EntityItemProperty;
import com.vaadin.data.Validator;
import brains.vframework.view.DefaultCrudView;

public class OneToManyPropertyPresenter<ITEM, REFERENCE> extends OneToManyPresenter<ITEM, REFERENCE> implements ListPropertyPresenter<REFERENCE, DefaultCrudView> {

    private EntityItemProperty propertyDataSource;

    public OneToManyPropertyPresenter(final String listProperty, final String mappedByProperty) {
        super(listProperty, mappedByProperty);
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
        for (EntityItem<REFERENCE> item : unsaved) {
            container().removeItem(item.getItemId());
        }
    }
}
