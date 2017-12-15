package brains.vframework.view;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

public class ListView extends BasicListView implements Serializable {
    public final ComboBox filterFields;
    public final TextField searchTxt;
    public final Button refreshBtn;

    public ListView() {
        searchTxt = new TextField();
		searchTxt.addStyleName("small borderless");

        refreshBtn = new Button("", FontAwesome.REFRESH);
        refreshBtn.addStyleName("small no-border");
        refreshBtn.addStyleName("circle-btn");

        CssLayout refreshGrp = new CssLayout();
        refreshGrp.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        refreshGrp.setWidth("100%");
        refreshGrp.addComponent(refreshBtn);

        filterFields = new ComboBox();
        filterFields.setInputPrompt("Поле для поиска");
        filterFields.addStyleName("small no-border");
        filterFields.setNullSelectionAllowed(false);
        filterFields.setFilteringMode(FilteringMode.CONTAINS);

        topToolbar2.addToLeft(filterFields);
        topToolbar2.addMaximized(searchTxt);
        topToolbar2.addToRightAtFirst(refreshGrp);
    }
}
