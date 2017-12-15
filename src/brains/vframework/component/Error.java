package brains.vframework.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.Serializable;

public class Error extends VerticalLayout implements Serializable {

    public final Label caption;
    public final Label message = new Label();

    public Error() {
        caption = new Label("Error");
        caption.addStyleName("error");

        VerticalLayout centerLayout = new VerticalLayout();
        centerLayout.setWidthUndefined();

        centerLayout.addComponent(caption);
        centerLayout.addComponent(message);
        centerLayout.setExpandRatio(message, 1);

        addStyleName("error-layout");

        addComponent(centerLayout);
        setComponentAlignment(centerLayout, Alignment.MIDDLE_CENTER);
    }
}
