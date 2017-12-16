package brains.vframework;

import brains.vframework.helpers.Beans;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import brains.vframework.spi.ContainerFilterProvider;

import java.io.Serializable;
import java.util.Set;

public class VContainers implements Serializable {

    private static final Set<ContainerFilterProvider> CONTAINER_FILTER_PROVIDERS = Beans.getBeans(ContainerFilterProvider.class);

    public static <T> JPAContainer<T> createContainer(final Class<T> forClass) {
        final JPAContainer<T> container = JPAContainerFactory.makeJndi(forClass);
        for (final ContainerFilterProvider filterProvider : CONTAINER_FILTER_PROVIDERS) {
            filterProvider.getFiltersFor(forClass).forEach(container::addContainerFilter);
        }
        return container;
    }

    public static JPAContainer createContainerUntyped(final Class forClass) {
        final JPAContainer container = JPAContainerFactory.makeJndi(forClass);
        for (final ContainerFilterProvider filterProvider : CONTAINER_FILTER_PROVIDERS) {
            filterProvider.getFiltersFor(forClass).forEach(container::addContainerFilter);
        }
        return container;
    }
}
