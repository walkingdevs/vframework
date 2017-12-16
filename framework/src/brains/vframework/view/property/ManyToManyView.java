package brains.vframework.view.property;

import brains.vframework.VMessages;
import brains.vframework.view.ListView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import brains.vframework.component.VWindow;
import brains.vframework.view.ItemSelectorView;
import brains.vframework.view.api.View;

import java.io.Serializable;

public class ManyToManyView extends ListView implements View, Serializable {

    public final ItemSelectorView itemSelectorView = new ItemSelectorView();

    public final Button add;
    public final Button remove;

    private final VWindow window;

    public ManyToManyView() {

        add = new Button(VMessages.getText("add_to_list"), FontAwesome.PLUS_CIRCLE);
        add.addStyleName("small borderless");

        remove = new Button(VMessages.getText("remove_from_list"), FontAwesome.TIMES_CIRCLE);
        remove.addStyleName("small borderless");

        topToolbar.addToRight(add);
        topToolbar.addToRight(remove);

        setContent(table);

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
