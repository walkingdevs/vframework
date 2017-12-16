package brains.vframework.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.NotImplementedException;

import java.io.Serializable;

public class DockLayout extends VerticalLayout implements Serializable {

	private final HorizontalLayout contentLayout = new HorizontalLayout();
	private final VerticalLayout topComponentsLayout = new VerticalLayout();
	private final VerticalLayout bottomComponentsLayout = new VerticalLayout();
	
	private Component content;

    public DockLayout(final Component content) {
        this();
        setContent(content);
    }

    public DockLayout(final Component content, final Component topComponent) {
        this(content);
        dockToTop(topComponent, false);
    }

    public DockLayout(final Component content, final Component topComponent, final Component bottomComponent) {
        this(content, topComponent);
        dockToBottom(bottomComponent);
    }

    public DockLayout() {
        topComponentsLayout.addStyleName("dock-layout-top");
        bottomComponentsLayout.addStyleName("dock-layout-bottom");

        contentLayout.addStyleName("dock-layout-content");
        contentLayout.setSizeFull();

        addComponent(contentLayout);
        setExpandRatio(contentLayout, 1);

        addStyleName("dock-layout");
        setSizeFull();
    }

    public void dock(final Component component, final Dock to, boolean boundary) {
        if (to == Dock.TOP) {
            dockToTop(component, boundary);
        } else if (to == Dock.BOTTOM) {
            dockToBottom(component);
        }  else if (to == Dock.LEFT) {
            throw new NotImplementedException("TODO!");
        } else if (to == Dock.RIGHT) {
            throw new NotImplementedException("TODO!");
        }
    }

	public void dockToTop(final Component component, boolean boundary) {
		if (boundary) {
            topComponentsLayout.addComponent(component, 0);
            component.addStyleName("dock-layout-top-boundary");
        } else {
            topComponentsLayout.addComponent(component);
        }

		if (getComponentIndex(topComponentsLayout) == -1) {
            addComponent(topComponentsLayout, 0);
        }

        component.setWidth("100%");
    }

    public void dockToBottom(final Component component) {
		component.setWidth("100%");
		bottomComponentsLayout.addComponent(component);
		if (getComponentIndex(bottomComponentsLayout) == -1) {
			addComponent(bottomComponentsLayout);
			setComponentAlignment(bottomComponentsLayout, Alignment.BOTTOM_LEFT);
		}
	}

	public Component getContent() {
		return content;
	}

	public void setContent(final Component content) {
		this.content = content;

        content.setSizeFull();

		contentLayout.removeAllComponents();
		contentLayout.addComponent(content);
	}

    public void unDockTop(final Component component) {
        topComponentsLayout.removeComponent(component);
    }

    public void unDockAllTop() {
        removeComponent(topComponentsLayout);
    }

    public void unDockAllBottom() {
        removeComponent(bottomComponentsLayout);
    }

    public enum Dock {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
}
