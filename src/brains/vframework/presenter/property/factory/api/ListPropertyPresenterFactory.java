package brains.vframework.presenter.property.factory.api;

import brains.vframework.presenter.api.ListPropertyPresenter;

import java.lang.reflect.Field;

public interface ListPropertyPresenterFactory extends PropertyPresenterFactory {

    <ITEM, REFERENCE> ListPropertyPresenter<REFERENCE, ?> create(final Class<ITEM> forClass, final Field forField);
}
