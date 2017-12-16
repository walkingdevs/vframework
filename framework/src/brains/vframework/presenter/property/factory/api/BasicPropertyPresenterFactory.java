package brains.vframework.presenter.property.factory.api;

import brains.vframework.presenter.api.PropertyPresenter;

import java.lang.reflect.Field;

public interface BasicPropertyPresenterFactory extends PropertyPresenterFactory {

    PropertyPresenter<?> create(final Class<?> forClass, final Field forField);
}
