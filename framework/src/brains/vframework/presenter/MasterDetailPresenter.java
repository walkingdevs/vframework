package brains.vframework.presenter;

import brains.vframework.VMessages;
import brains.vframework.annotation.Property;
import brains.vframework.presenter.api.ListPropertyPresenter;
import brains.vframework.presenter.property.factory.DefaultListPropertyPresenterFactory;
import brains.vframework.presenter.property.factory.api.ListPropertyPresenterFactory;
import brains.vframework.utils.ItemPropertyBinder;
import brains.vframework.view.BasicListView;
import brains.vframework.view.CrudView;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.server.Sizeable;
import brains.vframework.presenter.api.Presenter;
import brains.vframework.view.MasterDetailView;
import com.vaadin.ui.Label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MasterDetailPresenter<ITEM, VIEW extends MasterDetailView> extends CrudPresenter<ITEM, VIEW> implements Serializable {
    private final ItemPropertyBinder itemPropertyBinder = new ItemPropertyBinder();
    private final ListPropertyPresenterFactory listPropertyPresenterFactory = new DefaultListPropertyPresenterFactory();
    public final List<ListPropertyPresenter<?, ?>> listPropertyPresenters = new ArrayList<>();

    private boolean build = false;

    public MasterDetailPresenter() {
        listPresenter().addItemSelectedEventListener(event -> {
            if (!build) {
                build(event);
            }

            itemPropertyBinder.item(event);
        });
    }

    @Override
    public void view(final VIEW view) {
        super.view(view);

        itemFormPresenter().view().getWindow().addWindowCloseListener(windowCloseEvent -> listPropertyPresenters.forEach(Presenter::onReload));
    }

    private void build(final EntityItem<ITEM> item) {
        Property.Prioritized.fields(Property.Writables.ofClass(item.getEntity().getClass())).forEach(tuple -> {
            if (Property.Types.isList(tuple.one)) {
                ListPropertyPresenter<?, ?> propertyPresenter = listPropertyPresenterFactory.create(item.getEntity().getClass(), tuple.one);
                itemPropertyBinder.bind(propertyPresenter, tuple.one.getName());
                if (BasicListView.class.isAssignableFrom(propertyPresenter.view().getClass())) {
                    Label caption = ((BasicListView) propertyPresenter.view()).caption;
                    ((BasicListView) propertyPresenter.view()).topToolbar.removeComponent(caption);
                }
                if (CrudView.class.isAssignableFrom(propertyPresenter.view().getClass())) {
                    Label caption = ((CrudView) propertyPresenter.view()).getListView().caption;
                    ((CrudView) propertyPresenter.view()).getListView().topToolbar.removeComponent(caption);
                }
                view().getDetailsTabs().addTab(propertyPresenter.view(), VMessages.getText(tuple.one.getName()));
                listPropertyPresenters.add(propertyPresenter);
            }
        });
        if (view().getDetailsTabs().getComponentCount() < 1) {
            view().removeComponent(view().getDetailsTabs());
            view().setSplitPosition(0, Sizeable.Unit.PIXELS, true);

            view().getWindow().setWidth("500px");
        }
        build = true;
    }
}
