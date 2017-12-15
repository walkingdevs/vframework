package brains.vframework.view;

import brains.vframework.VMessages;
import brains.vframework.component.*;
import brains.vframework.view.api.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;
import java.util.Locale;

public class BasicListView extends DockLayout implements View, Serializable {

    public final VTable table;
    public final Button pageIndex;
    public final Button pageCount;
    public final Label caption;
    public final Button total;
    public final Button firstBtn;
    public final Button prevBtn;
    public final Button nextBtn;
    public final Button lastBtn;
    public final Button toggleAdditionalColumnsBtn;
    public final HLayout topToolbar;
    public final HLayout topToolbar2;

    private final VWindow window;

    public BasicListView() {
        caption = new Label();
        caption.addStyleName("caption");

        total = new Button("Всего: ");
        total.addStyleName("small no-border");

        firstBtn = new Button("", FontAwesome.ANGLE_DOUBLE_LEFT);
        firstBtn.addStyleName("small no-border");

        prevBtn = new Button("", FontAwesome.ANGLE_LEFT);
        prevBtn.addStyleName("small no-border");

        nextBtn = new Button("", FontAwesome.ANGLE_RIGHT);
        nextBtn.addStyleName("small no-border");

        lastBtn = new Button("", FontAwesome.ANGLE_DOUBLE_RIGHT);
        lastBtn.addStyleName("small no-border");

        pageIndex = new Button("стр. 1");
        pageIndex.addStyleName("small no-border");

        pageCount = new Button("1");
        pageCount.addStyleName("small no-border");

        Button separator = new Button("из ");
        separator.addStyleName("small no-border");

        toggleAdditionalColumnsBtn = new Button(VMessages.getText("show_add_columns"), FontAwesome.PLUS_CIRCLE);
        toggleAdditionalColumnsBtn.addStyleName("small borderless");

        CssLayout pagingInfo = new CssLayout();
        pagingInfo.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        pagingInfo.setWidth("100%");
        pagingInfo.addComponent(pageIndex);
        pagingInfo.addComponent(separator);
        pagingInfo.addComponent(pageCount);

        CssLayout totalGrp = new CssLayout();
        totalGrp.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        totalGrp.setWidth("100%");
        totalGrp.addComponent(total);

        CssLayout pagingGroup = new CssLayout();
        pagingGroup.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        pagingGroup.setWidth("100%");
        pagingGroup.addComponent(firstBtn);
        pagingGroup.addComponent(prevBtn);
        pagingGroup.addComponent(nextBtn);
        pagingGroup.addComponent(lastBtn);

        // Table

        table = new BasicPagedTable();
        table.setImmediate(true);
        table.setMultiSelect(true);
        table.setBuffered(false);
        table.setNullSelectionAllowed(false);
        table.setMultiSelectMode(MultiSelectMode.DEFAULT);
        table.setPageLength(30);
        table.setRowHeaderMode(Table.RowHeaderMode.INDEX);

        // Main toolbar

		topToolbar = new HLayout();
        topToolbar.addStyleName("toolbar-default");
        topToolbar.addToLeft(caption);
        topToolbar.addToRight(toggleAdditionalColumnsBtn);

        // Bottom toolbar

        topToolbar2 = new HLayout();
        topToolbar2.addStyleName("toolbar-default");
        topToolbar2.addToRight(pagingInfo);
        topToolbar2.addToRight(pagingGroup);
        topToolbar2.addToRight(totalGrp);

        // Content

        dockToTop(topToolbar, false);
        dockToTop(topToolbar2, false);
        setContent(table);

        // Paging hack
        table.setHeightUndefined();
        table.setWidth("100%");

        addStyleName("basic-list-view dock-layout-default");
        setSizeFull();

        window = new VWindow(this);
        window.width("98%").height("98%");
        window.setModal(true);
        window.setResizable(false);
    }

    @Override
    public VWindow getWindow() {
        return window;
    }

    @Override
    public void setLocale(final Locale locale) {
        super.setLocale(locale);
    }

    public void setCaption(String caption) {
        this.caption.setValue(caption);
    }
}
