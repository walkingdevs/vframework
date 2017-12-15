package brains.vframework.presenter.property;

import brains.vframework.VContext;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.EntityItemProperty;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.Validator;
import brains.vframework.event.api.ItemEventListener;
import brains.vframework.helpers.Reflections;
import brains.vframework.presenter.ItemFormPresenter;
import brains.vframework.presenter.ItemSingleSelectorPresenter;
import brains.vframework.presenter.api.ReferencePropertyPresenter;
import brains.vframework.view.ItemFormView;
import brains.vframework.view.ItemSelectorView;
import brains.vframework.view.property.ManyToOnePropertyView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ManyToOnePropertyPresenter<REFERENCE> implements ReferencePropertyPresenter<REFERENCE, ManyToOnePropertyView>, Serializable {

    private static final Logger log = Logger.getLogger(ManyToOnePropertyPresenter.class.getName());

    private ManyToOnePropertyView view;
    private EntityContainer<REFERENCE> container;
    private EntityItemProperty propertyDataSource;
    private Object initialValue = null;
    private REFERENCE value;

    private final List<ItemEventListener<REFERENCE>> itemChanegedListeners = new ArrayList<>();

    @Override
    public void view(final ManyToOnePropertyView view) {
        this.view = view;

        view.comboBox.setConverter(new SingleSelectConverter<>(view.comboBox));
        view.comboBox.addValueChangeListener(event -> {
            REFERENCE entity = null;
            if (view.comboBox.getValue() != null) {
                entity = container.getItem(view.comboBox.getValue()).getEntity();
            }
            if (propertyDataSource != null) {
                propertyDataSource.setValue(entity);
            }
            value = entity;
            notifyItemChangedListeners(container.getItem(view.comboBox.getValue()));
        });
        view.newBtn.addClickListener(e -> {
            EntityItem<REFERENCE> newItem = container.createEntityItem(VContext.getBean(container.getEntityClass()));
            ItemFormPresenter<REFERENCE> itemFormPresenter = new ItemFormPresenter<>();
            itemFormPresenter.view(new ItemFormView());
            itemFormPresenter.item(newItem);
            itemFormPresenter.addItemCreatedEventListener(o -> view.comboBox.setValue(o.getItemId()));
            itemFormPresenter.view().open();
        });
        view.editBtn.addClickListener(e -> {
            ItemSingleSelectorPresenter<REFERENCE> selectorPresenter = new ItemSingleSelectorPresenter<>();
            selectorPresenter.view(new ItemSelectorView());
            selectorPresenter.container(container);
            selectorPresenter.view().open();
            selectorPresenter.addItemSelectedEventListener(o -> view.comboBox.setValue(o.getItemId()));
        });
    }

    @Override
    public void propertyDataSource(final EntityItemProperty propertyDataSource) {
        this.propertyDataSource = propertyDataSource;

        Object id = null;
        try {
            id = Reflections.getValue(propertyDataSource.getValue(), "id");
        } catch (NullPointerException npe) {
            // Its Ok.
        } catch (IllegalAccessException e) {
            log.severe("WTF?");
        }

        view.comboBox.setValue(id);
        initialValue = id;
    }

    @Override
    public ManyToOnePropertyView view() {
        return view;
    }

    @Override
    public EntityContainer<REFERENCE> container() {
        return container;
    }

    @Override
    public void container(final EntityContainer<REFERENCE> container) {
        this.container = container;
        view.comboBox.setContainerDataSource(container);
    }

    @Override
    public void onReload() {
    }

    @Override
    public EntityItemProperty propertyDataSource() {
        return propertyDataSource;
    }

    @Override
    public void addValidator(final Validator validator) {
        view.comboBox.addValidator(validator);
    }

    @Override
    public void removeValidator(final Validator validator) {
        view.comboBox.removeValidator(validator);
    }

    @Override
    public boolean isModified() {
        try {
            view.comboBox.validate();
            return  initialValue != null && view.comboBox.getValue() == null ||
                    initialValue == null && view.comboBox.getValue() != null ||
                    !(initialValue == null || initialValue.equals(view.comboBox.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void discard() {
    }

    private void notifyItemChangedListeners(final EntityItem<REFERENCE> entityItem) {
        for (final ItemEventListener<REFERENCE> listener : itemChanegedListeners) {
            listener.onEvent(entityItem);
        }
    }

    public void addItemChangedListener(final ItemEventListener<REFERENCE> listener) {
        itemChanegedListeners.add(listener);
    }

    @Override
    public void setValue(final REFERENCE value) {
        this.value = value;
        Object id = null;
        try {
            id = Reflections.getValue(value, "id");
        } catch (NullPointerException npe) {
            // Its Ok.
        } catch (IllegalAccessException e) {
            log.severe("WTF?");
        }

        view.comboBox.setValue(id);
    }

    @Override
    public REFERENCE getValue() {
        return null;
    }
}
