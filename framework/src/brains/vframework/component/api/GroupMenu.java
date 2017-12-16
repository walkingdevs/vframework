package brains.vframework.component.api;

import com.vaadin.server.Resource;

public interface GroupMenu extends Menu {

    void addGroupTitle(final String title, final Resource icon);
}
