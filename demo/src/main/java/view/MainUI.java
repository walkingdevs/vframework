package view;

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

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Button button = new Button("Create", (event) -> {
        });
        Button button2 = new Button("Get", (event) -> {
        });

        VerticalLayout layout = new VerticalLayout(
            button,
            button2
        );
        layout.setSizeFull();
        setContent(layout);
    }
}