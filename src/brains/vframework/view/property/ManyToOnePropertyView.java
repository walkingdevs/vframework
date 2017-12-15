package brains.vframework.view.property;

import brains.vframework.view.api.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import brains.vframework.component.VWindow;

import java.io.Serializable;

public class ManyToOnePropertyView extends HorizontalLayout implements View, Serializable {

	public final Button newBtn;
    public final Button editBtn;
    public final ComboBox comboBox;

    private final VWindow window;

    public ManyToOnePropertyView() {

        comboBox = new ComboBox();
        comboBox.setSizeFull();
        comboBox.addStyleName("small");
        comboBox.setNullSelectionAllowed(true);
        comboBox.setFilteringMode(FilteringMode.CONTAINS);
        comboBox.setItemCaptionMode(NativeSelect.ITEM_CAPTION_MODE_ITEM);
        comboBox.setScrollToSelectedItem(true);
        comboBox.setImmediate(true);

        newBtn = new Button("", FontAwesome.PLUS_CIRCLE);
        newBtn.addStyleName("small");
        newBtn.setHeight(100, Unit.PERCENTAGE);
        newBtn.setTabIndex(-1);

        editBtn = new Button("", FontAwesome.PENCIL);
        editBtn.setHeight(100, Unit.PERCENTAGE);
        editBtn.addStyleName("small");
        editBtn.setTabIndex(-1);

        addComponent(comboBox);
        addComponent(newBtn);
        addComponent(editBtn);

        setSpacing(true);
        setExpandRatio(comboBox, 1);
        setWidth("100%");

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
