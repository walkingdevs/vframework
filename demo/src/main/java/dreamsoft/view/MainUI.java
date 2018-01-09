package dreamsoft.view;

import brains.vframework.VUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.guice.annotation.GuiceUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@Title("Main UI")
@Theme("blue")
@GuiceUI
public class MainUI extends VUI {

//    @Inject
//    PersonDAO dao;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Button button = new Button();
        setContent(new VerticalLayout(button));
    }
}