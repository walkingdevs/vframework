package brains.vframework.helpers;

import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractComponent;

public class Shortcuts {
    public static ShortcutListener addShortcutListener(final AbstractComponent actionSource, final String caption, final int keyCode, final Runnable action) {
        ShortcutListener listener = new ShortcutListener(caption, keyCode, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (target == actionSource) action.run();
            }
        };
        actionSource.addShortcutListener(listener);
        return listener;
    }
}
