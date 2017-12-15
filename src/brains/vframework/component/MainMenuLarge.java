package brains.vframework.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import brains.vframework.component.api.Menu;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MainMenuLarge extends DockLayout implements Menu, Serializable {

    private final CssLayout layout;
    private final Set<Button> navButtons = new HashSet<>();

    public MainMenuLarge() {
        layout = new CssLayout();
        addStyleName("menu-large");
        setContent(layout);
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
        layout.addComponent(button);
        button.setHtmlContentAllowed(true);
    }

    @Override
    public void addItem(final Component component) {
        layout.addComponent(component);
    }
}