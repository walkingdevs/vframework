package brains.vframework.presenter.property.factory;

import brains.vframework.presenter.api.ListPropertyPresenter;
import brains.vframework.presenter.property.factory.api.ListPropertyPresenterFactory;
import brains.vframework.view.DefaultCrudView;
import brains.vframework.annotation.Pivot;
import brains.vframework.VContainers;
import brains.vframework.presenter.property.ManyToManyPropertyPresenter;
import brains.vframework.presenter.property.OneToManyPropertyPresenter;
import brains.vframework.view.property.ManyToManyView;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultListPropertyPresenterFactory implements ListPropertyPresenterFactory, Serializable {

    private static final Logger logger = Logger.getLogger(DefaultListPropertyPresenterFactory.class.getName());

    @Override
    public <ITEM, REFERENCE> ListPropertyPresenter<REFERENCE, ?> create(final Class<ITEM> forClass, final Field forField) {
        logger.log(Level.FINE, "Creating ListPropertyPresenter for field [{0}]", forField);

        OneToMany oneToMany = forField.getAnnotation(OneToMany.class);
        Pivot pivot = forField.getAnnotation(Pivot.class);

        if (pivot != null) {
            return forManyToMany(forField, oneToMany, pivot);
        } else {
            return forOneToMany(forField, oneToMany);
        }
    }

    private <ITEM, REFERENCE, PIVOT> ListPropertyPresenter<PIVOT, ?> forManyToMany(final Field forField, final OneToMany oneToMany, final Pivot pivot) {
        ParameterizedType genericSuperclass = (ParameterizedType) forField.getGenericType();
        Class<PIVOT> pivotClass = (Class<PIVOT>) genericSuperclass.getActualTypeArguments()[0];

        Class<REFERENCE> referenceClass = null;
        try {
            Field field = pivotClass.getDeclaredField(pivot.property());
            field.setAccessible(true);
            referenceClass = (Class<REFERENCE>) field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        ManyToManyPropertyPresenter<ITEM, REFERENCE, PIVOT> presenter = new ManyToManyPropertyPresenter<>(forField.getName(), oneToMany.mappedBy(), pivot.property(), referenceClass);
        presenter.view(new ManyToManyView());
        presenter.container(VContainers.createContainer(pivotClass));

        return presenter;
    }

    private <ITEM, REFERENCE> ListPropertyPresenter<REFERENCE, ?> forOneToMany(final Field forField, final OneToMany oneToMany) {
        ParameterizedType genericSuperclass = (ParameterizedType) forField.getGenericType();
        Class<REFERENCE> referencedType = (Class<REFERENCE>) genericSuperclass.getActualTypeArguments()[0];

        OneToManyPropertyPresenter<ITEM, REFERENCE> presenter = new OneToManyPropertyPresenter<>(forField.getName(), oneToMany.mappedBy());
        presenter.view(new DefaultCrudView());
        presenter.container(VContainers.createContainer(referencedType));

        return presenter;
    }
}