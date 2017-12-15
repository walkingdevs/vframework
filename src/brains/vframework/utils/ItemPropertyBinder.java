package brains.vframework.utils;

import brains.vframework.presenter.api.PropertyPresenter;
import com.vaadin.addon.jpacontainer.EntityItem;

import java.util.HashMap;
import java.util.Map;

public class ItemPropertyBinder {

    private final Map<String, PropertyPresenter<?>> map = new HashMap<>();

    public void bind(final PropertyPresenter<?> propertyPresenter, final String propertyId) {
        map.put(propertyId, propertyPresenter);
    }

    public void item(final EntityItem<?> item) {
        map.forEach((propertyId, propertyPresenter) -> propertyPresenter.propertyDataSource(item.getItemProperty(propertyId)));
    }

    public boolean isAnyPropertyModified() {
        return map.values().stream().anyMatch(PropertyPresenter::isModified);
    }

    public void discardAll() {
        map.values().forEach(PropertyPresenter::discard);
    }

    public Map<String, PropertyPresenter<?>> getMap() {
        Map<String, PropertyPresenter<?>> copy = new HashMap<>();
        map.forEach(copy::put);

        return copy;
    }
}
