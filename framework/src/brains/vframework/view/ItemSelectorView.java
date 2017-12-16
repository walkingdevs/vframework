package brains.vframework.view;

import brains.vframework.VMessages;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

import java.io.Serializable;

public class ItemSelectorView extends DefaultCrudView implements Serializable {

    private final Button selectBtn;

    public ItemSelectorView() {
        selectBtn = new Button(VMessages.getText("select"), FontAwesome.CHECK_CIRCLE_O);
        selectBtn.addStyleName("borderless small");

        getWindow().getBottomToolbar().addToRightAtFirst(selectBtn);
    }

    public Button getSelectBtn() {
        return selectBtn;
    }
}