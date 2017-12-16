package brains.vframework.event.source;

import brains.vframework.event.api.ActionListener;

public interface ContainerRefreshedEventSource {

    void addContainerRefreshedEventListener(final ActionListener listener);
    void removeContainerRefreshedEventListener(final ActionListener listener);
}
