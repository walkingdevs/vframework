package brains.vframework.view;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import brains.vframework.VMessages;

import java.io.Serializable;

public class ItemEditorView extends ItemView implements Serializable {

    public final Button saveBtn;
    public final Button saveTopBtn;

    public ItemEditorView() {
        super(new VerticalLayout());

		saveBtn = new Button(VMessages.getText("save"), FontAwesome.DATABASE);
		saveBtn.addStyleName("borderless small");

        saveTopBtn = new Button("", FontAwesome.DATABASE);
        saveTopBtn.addStyleName("borderless small");
        saveTopBtn.setDescription(VMessages.getText("save"));

        topToolbar.addToRight(saveBtn);

        content.setSplitPosition(0, true);
	}
}
