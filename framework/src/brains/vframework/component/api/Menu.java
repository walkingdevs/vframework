package brains.vframework.component.api;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

public interface Menu {

    void addItem(final Resource icon, final String caption, final Runnable action);
    void addItem(final Component component);
}
