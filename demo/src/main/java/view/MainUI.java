package view;

import brains.vframework.VUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.guice.annotation.GuiceUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;


@Title("Main UI")
@Theme("blue")
@GuiceUI
public class MainUI extends VUI {

    @Inject
    Service1 service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(new Button(service.run()));
    }
}