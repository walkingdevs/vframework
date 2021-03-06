package brains.view;

import brains.vframework.VUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@Title("Main UI")
@Theme("blue")
public class MainUI extends VUI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Button button = new Button();
        setContent(new VerticalLayout(button));
    }
}