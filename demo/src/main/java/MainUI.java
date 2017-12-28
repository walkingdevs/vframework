import brains.vframework.VUI;
import brains.vframework.helpers.Notifications;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.guice.annotation.GuiceUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import domain.Person;


@Title("Main UI")
@Theme("blue")
@GuiceUI
public class MainUI extends VUI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Button button = new Button("Create", (event) -> {
            Person person = new Person();
            person.setLazy("dddd---------------dsadsadk;oskaldsaldlsad;dsa");
            Notifications.warning(person.getId() + "");
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