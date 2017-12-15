package brains.vframework.view;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import brains.vframework.VMessages;
import brains.vframework.component.DockLayout;
import brains.vframework.component.HLayout;
import brains.vframework.component.VWindow;
import brains.vframework.view.api.View;

import java.io.Serializable;

public class TreeView extends DockLayout implements View, Serializable {

    public final HLayout toolbar;
    public final Tree tree;
    public final Button expandAll;
    public final Button collapseAll;

    private final VWindow window;

    public TreeView() {
        expandAll = new Button("", FontAwesome.EXPAND);
        expandAll.addStyleName("small");

        collapseAll = new Button("", FontAwesome.COMPRESS);
        collapseAll.addStyleName("small");

        toolbar = new HLayout();
        toolbar.addStyleName("toolbar-default");
//        toolbar.addToLeft(expandAll);
//        toolbar.addToLeft(collapseAll);

        tree = new Tree();
        tree.setSizeFull();
        tree.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);

        dockToTop(toolbar, false);
        setContent(tree);

        expandAll.setCaption(VMessages.getText("expand_all"));
        collapseAll.setCaption(VMessages.getText("collapse_all"));

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