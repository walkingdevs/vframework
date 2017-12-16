package brains.vframework.component;

import com.vaadin.ui.*;
import brains.vframework.event.api.ActionListener;

import java.io.Serializable;

public class Dialog extends VWindow implements Serializable {

    public final HorizontalLayout content = new HorizontalLayout();

    public Dialog(final Component component) {
        content.addComponent(component);
        content.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        content.setMargin(true);

        content(content);

        setResizable(false);
        setModal(true);
    }

    public Dialog addAction(final String caption, final ActionListener listener) {
        Button button = new Button(caption);
        button.addStyleName("small no-border");
        button.addClickListener(clickEvent -> {
            listener.onAction();
            close();
        });

        getBottomToolbar().addToRightAtFirst(button);

        return this;
    }
}
