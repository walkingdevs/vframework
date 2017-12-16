package brains.vframework.view;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;
import brains.vframework.component.DockLayout;
import brains.vframework.component.HLayout;
import brains.vframework.component.VWindow;
import brains.vframework.view.api.View;

import java.io.Serializable;

public class ItemView extends DockLayout implements View, Serializable {

    public final AbstractOrderedLayout fieldLayout;

    public final VerticalSplitPanel content;
    public final HLayout topToolbar;
    public final Label title;

    private final VWindow window;

    public ItemView(final AbstractOrderedLayout fieldLayout) {
        this.fieldLayout = fieldLayout;

        fieldLayout.setMargin(true);
        fieldLayout.setSpacing(true);

        title = new Label("", ContentMode.HTML);
        title.addStyleName("title");

        topToolbar = new HLayout();
        topToolbar.addStyleName("toolbar-default");
        topToolbar.addToLeft(title);

        content = new VerticalSplitPanel();
        content.setSizeFull();
        content.addComponent(fieldLayout);

        setContent(content);
        dockToTop(topToolbar, false);

        setSizeFull();
        addStyleName("item-view dock-layout-default");

        window = new VWindow(this);
        window.width("98%").height("98%");
        window.setModal(true);
        window.setResizable(false);
    }

    @Override
    public VWindow getWindow() {
        return window;
    }
}
