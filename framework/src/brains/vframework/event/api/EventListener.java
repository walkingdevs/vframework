package brains.vframework.event.api;

public interface EventListener<EVENT> {

    void onEvent(final EVENT event);
}
