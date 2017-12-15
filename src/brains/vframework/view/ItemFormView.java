package brains.vframework.view;

import brains.vframework.VMessages;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

public class ItemFormView extends ItemView implements Serializable {

    public final Button saveBtn;
    public final Button saveAndCloseBtn;
    public final Button saveBottomBtn;
    public final Button saveAndCloseBottomBtn;
    public final TabSheet tabSheet = new TabSheet();

    public ItemFormView() {
        super(new FormLayout());

        saveBtn = new Button(VMessages.getText("save"), FontAwesome.DATABASE);
        saveBtn.addStyleName("small borderless");

        saveAndCloseBtn = new Button(VMessages.getText("save_and_close"), FontAwesome.DATABASE);
        saveAndCloseBtn.addStyleName("small borderless");

        saveBottomBtn = new Button(VMessages.getText("save"), FontAwesome.DATABASE);
        saveBottomBtn.addStyleName("small borderless");

        saveAndCloseBottomBtn = new Button(VMessages.getText("save_and_close"), FontAwesome.DATABASE);
        saveAndCloseBottomBtn.addStyleName("small borderless");

        tabSheet.setSizeFull();
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        content.addComponent(tabSheet);
        content.setSplitPosition(50, Unit.PERCENTAGE, true);

        topToolbar.addToRight(saveBtn);
        topToolbar.addToRight(saveAndCloseBtn);

        addStyleName("item-form-view");
    }
}
