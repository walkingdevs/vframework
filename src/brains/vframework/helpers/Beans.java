package brains.vframework.helpers;

import org.apache.openejb.OpenEJBRuntimeException;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.container.BeanManagerImpl;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Beans {

    private static final BeanManagerImpl beanManager;

    static {
        // Todo: Плохо. Зависим от OWB.
        beanManager = WebBeansContext.currentInstance().getBeanManagerImpl();
        if (!beanManager.isInUse()) {
            throw new OpenEJBRuntimeException("CDI not activated");
        }
    }

    public static <T> T getBean(final Class<T> _class, Annotation... annotations) {

        Set<Bean<?>> beans = beanManager.getBeans(_class, annotations);
        if (beans.size() == 0) return null;

        Bean<T> bean = (Bean<T>) beans.iterator().next();
        CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
        T instance = (T) beanManager.getReference(bean, _class, creationalContext);

        return instance;
    }

    public static <T> Set<T> getBeans(final Class<T> _class) {

        Set<T> beans = new HashSet<>();
        for (Bean<?> bean : beanManager.getBeans(_class)) {
            CreationalContext<T> creationalContext = beanManager.createCreationalContext((Bean<T>) bean);
            T instance = (T) beanManager.getReference(bean, _class, creationalContext);
            beans.add(instance);
        }
        return beans;
    }

    public static <T, S extends T> List<Class<S>> getSubTypesOf(final Class<T> _class) {

        List<Class<S>> beans = new ArrayList<>();
        for (Bean<?> bean : beanManager.getBeans(_class, new AnnotationLiteral<Any>() {})) {
            beans.add((Class<S>) bean.getBeanClass());
        }
        return beans;
    }
}
