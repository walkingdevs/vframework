package brains.vframework;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

import javax.annotation.Resource;
import javax.transaction.UserTransaction;

@Widgetset("brains.vframework.AppWidgetSet")
@Theme("brown")
public abstract class VUI extends UI {

    public static final String SUFFIX = "UI";

    private CssLayout top = new CssLayout();
    private CssLayout content = new CssLayout();
    private CssLayout bottom = new CssLayout();

    @Resource
    private UserTransaction userTransaction;

    public static VUI current() {
        return (VUI) UI.getCurrent();
    }

    public VUI() {
        top.setSizeFull();
        content.setSizeFull();
        bottom.setSizeFull();
        setSizeFull();
    }

    public CssLayout content() {
        return content;
    }

    public CssLayout top() {
        return top;
    }

    public CssLayout bottom() {
        return bottom;
    }

    public UserTransaction getUserTransaction() {
        return userTransaction;
    }
}
