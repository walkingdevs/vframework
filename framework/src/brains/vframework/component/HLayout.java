package brains.vframework.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import java.io.Serializable;

public class HLayout extends HorizontalLayout implements Serializable {

	private final HorizontalLayout maxLayout;
	private final HorizontalLayout leftLayout;
	private final HorizontalLayout rightLayout;

	public HLayout() {

		maxLayout = new HorizontalLayout();
        maxLayout.setSpacing(true);
        maxLayout.setSizeFull();

        leftLayout = new HorizontalLayout();
        leftLayout.setSpacing(true);
        leftLayout.setHeight(100, Unit.PERCENTAGE);

        rightLayout = new HorizontalLayout();
        rightLayout.setSpacing(true);
        rightLayout.setHeight(100, Unit.PERCENTAGE);

		addComponent(leftLayout);
		addComponent(maxLayout);
		addComponent(rightLayout);

        setComponentAlignment(leftLayout, Alignment.TOP_LEFT);
        setComponentAlignment(maxLayout, Alignment.TOP_CENTER);
		setComponentAlignment(rightLayout, Alignment.TOP_RIGHT);
		setExpandRatio(maxLayout, 1);

		setSpacing(true);
		setMargin(true);
		addStyleName("h-layout no-y-scroll");
		setWidth("100%");
	}

	public void addMaximized(Component component) {
		component.setSizeFull();
		maxLayout.addComponent(component);
        maxLayout.setComponentAlignment(component, Alignment.TOP_CENTER);
	}

	public void addToLeft(Component component) {
		component.setHeight(100, Unit.PERCENTAGE);
		leftLayout.addComponent(component);
        leftLayout.setComponentAlignment(component, Alignment.TOP_LEFT);
	}

    public void addToLeft(Component component, Alignment alignment) {
		component.setHeight(100, Unit.PERCENTAGE);
        leftLayout.addComponent(component);
        leftLayout.setComponentAlignment(component, alignment);
    }

    public void addToRight(Component component) {
		component.setHeight(100, Unit.PERCENTAGE);
		rightLayout.addComponent(component);
        rightLayout.setComponentAlignment(component, Alignment.TOP_RIGHT);
	}

    public void addToRightAtFirst(Component component) {
		component.setHeight(100, Unit.PERCENTAGE);
        rightLayout.addComponent(component, 0);
        rightLayout.setComponentAlignment(component, Alignment.TOP_RIGHT);
    }

    @Override
    public void removeAllComponents() {
        leftLayout.removeAllComponents();
        maxLayout.removeAllComponents();
        rightLayout.removeAllComponents();
    }

    @Override
    public void removeComponent(final Component component) {
        leftLayout.removeComponent(component);
        maxLayout.removeComponent(component);
        rightLayout.removeComponent(component);
    }
}