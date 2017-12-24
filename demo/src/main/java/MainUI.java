import brains.vframework.VUI;
import brains.vframework.view.ListView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.VerticalLayout;

@Title("Main UI")
@Theme("blue")
public class MainUI extends VUI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout(new ListView());
        layout.setSizeFull();
        setContent(layout);
    }
}