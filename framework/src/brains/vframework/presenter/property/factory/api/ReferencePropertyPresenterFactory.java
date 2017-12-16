package brains.vframework.presenter.property.factory.api;

import brains.vframework.presenter.api.ReferencePropertyPresenter;

import java.lang.reflect.Field;

public interface ReferencePropertyPresenterFactory extends PropertyPresenterFactory {

    <ITEM, REFERENCE> ReferencePropertyPresenter<REFERENCE, ?> create(final Class<ITEM> forClass, final Field forField);
}
