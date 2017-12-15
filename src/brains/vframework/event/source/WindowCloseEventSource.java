package brains.vframework.event.source;

import brains.vframework.event.api.EventListener;
import brains.vframework.event.WindowCloseEvent;

public interface WindowCloseEventSource {

    void addWindowCloseListener(final EventListener<WindowCloseEvent> listener);
    void removeWindowCloseListener(final EventListener<WindowCloseEvent> listener);
}