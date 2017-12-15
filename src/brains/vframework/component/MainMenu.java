package brains.vframework.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;
import brains.vframework.component.api.GroupMenu;

import java.io.Serializable;
import java.util.*;

public class MainMenu extends DockLayout implements GroupMenu, Serializable {

    private final Set<Button> navButtons = new HashSet<>();

    private final CssLayout menuRoot;
	private final CssLayout itemsRoot;
	private final CssLayout itemsLayout;

    private final Map<String, List<Button>> groups = new HashMap<>();
    private String currentGroup;

    public MainMenu() {

        menuRoot = new CssLayout();
        menuRoot.setPrimaryStyleName("valo-menu");

        itemsRoot = new CssLayout();
        itemsRoot.addStyleName("valo-menu-part");
        itemsRoot.setSizeFull();

        itemsLayout = new CssLayout();
        itemsLayout.setSizeFull();
        itemsLayout.setPrimaryStyleName("valo-menuitems");

        itemsRoot.addComponent(itemsLayout);

        menuRoot.addComponent(itemsLayout);

        addStyleName("menu");
        setContent(menuRoot);
	}

    @Override
    public void addItem(Resource icon, String caption, final Runnable action) {
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
        button.setPrimaryStyleName("valo-menu-item");
        itemsLayout.addComponent(button);
        button.setHtmlContentAllowed(true);

        if (currentGroup != null) {
            groups.get(currentGroup).add(button);
        }
	}

    @Override
    public void addItem(final Component component) {
        itemsLayout.addComponent(component);
    }

    @Override
	public void addGroupTitle(String title, Resource icon) {
		NativeButton label = new NativeButton(title);
		label.setIcon(icon);
		label.addStyleName("valo-menu-subtitle borderless");
		label.setWidth("100%");
        label.addClickListener(clickEvent -> groups.get(title).forEach(button -> button.setVisible(!button.isVisible())));

		itemsLayout.addComponent(label);

        groups.put(title, new ArrayList<>());
        currentGroup = title;
	}

    private void clearNavSelection() {
        navButtons.forEach(nativeButton -> nativeButton.removeStyleName("selected"));
    }
}
