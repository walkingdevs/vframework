package brains.vframework.component;

import brains.vframework.component.api.Menu;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AppLayout extends DockLayout implements Menu, Serializable {

    private final HorizontalSplitPanel center = new HorizontalSplitPanel();
    private final CssLayout menuLayout;
    private final Set<Button> navButtons = new HashSet<>();

    public AppLayout() {
        menuLayout = new CssLayout();
        menuLayout.setSizeFull();

        center.setSizeFull();
        center.addComponent(menuLayout);
        center.setSplitPosition(140, Unit.PIXELS);

        setSizeFull();
        addStyleName("app-layout");
        setContent(center);
    }

    private void clearNavSelection() {
        navButtons.forEach(nativeButton -> nativeButton.removeStyleName("selected"));
    }

    @Override
    public void addItem(Resource icon, String caption, Runnable action) {
        NativeButton button = new NativeButton(caption);

        if (icon != null) button.setIcon(icon);
        else button.setIcon(FontAwesome.CIRCLE_THIN);

        navButtons.add(button);
        button.addClickListener(clickEvent -> {
            clearNavSelection();
            clickEvent.getButton().addStyleName("selected");
            action.run();
        });

        button.setWidth("100%");
        menuLayout.addComponent(button);
        button.setHtmlContentAllowed(true);
    }

    @Override
    public void addItem(final Component component) {
        menuLayout.addComponent(component);
    }

    public void view(Component component) {
        component.setSizeFull();
        center.addComponent(component);
    }
}