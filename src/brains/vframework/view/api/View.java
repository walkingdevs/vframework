package brains.vframework.view.api;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import brains.vframework.component.VWindow;

public interface View extends Component {

    default void open() {
        UI.getCurrent().addWindow(getWindow());
    }

    default void close() {
        getWindow().close();
    }

    VWindow getWindow();
}
