package brains.vframework.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.Serializable;

public class TextButtonField extends CssLayout implements Serializable {

    private final TextField textField;
    private final Button button;

    public TextButtonField(String placeHolder, String buttonDesc, Resource buttonIcon, Resource textIcon) {
        addStyleName("v-component-group");

        textField = new TextField();
        textField.setWidth("76%");
        textField.setInputPrompt(placeHolder);
        textField.addStyleName("small");
        textField.addStyleName("inline-icon");

        if (textIcon == null) textIcon = FontAwesome.CIRCLE_THIN;
        textField.setIcon(textIcon);

        button = new Button("", buttonIcon);
        button.setDescription(buttonDesc);
        button.setWidth("24%");
        button.addStyleName("small");

        addComponent(textField);
        addComponent(button);
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getButton() {
        return button;
    }
}
