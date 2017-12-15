package brains.vframework.spi;

import com.vaadin.data.Container;

import java.util.List;

public interface ContainerFilterProvider {

    List<Container.Filter> getFiltersFor(final Class<?> _class);
}
