package view;

import brains.vframework.VUI;
import brains.vframework.view.ListView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.guice.annotation.GuiceUI;
import com.vaadin.server.VaadinRequest;


@Title("Main UI")
@Theme("blue")
@GuiceUI
public class MainUI extends VUI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new ListView());
    }
}