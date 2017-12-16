package brains.vframework.component;

import brains.vframework.VMessages;
import brains.vframework.event.WindowCloseEvent;
import brains.vframework.event.api.EventListener;
import brains.vframework.event.source.WindowCloseEventSource;
import brains.vframework.helpers.Shortcuts;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VWindow extends Window implements WindowCloseEventSource, Serializable {

    private final Label caption;
    private final Button closeBtn;
    private final DockLayout layout;
    private final HLayout bottomToolbar;

    private final List<EventListener<WindowCloseEvent>> closeEventListeners = new ArrayList<>();
    private Component content;

    public VWindow(final Component content) {
        this();
        content(content);
    }

    public VWindow(final Component content, Component... toolbarComponents) {
        this();
        content(content);
        for (Component toolbarComponent : toolbarComponents) {
            bottomToolbar.addToRightAtFirst(toolbarComponent);
        }
    }

    public VWindow() {
        caption = new Label();

        closeBtn = new Button(VMessages.getText("close"));
        closeBtn.addStyleName("borderless small");
        closeBtn.setIcon(FontAwesome.TIMES_CIRCLE);
        closeBtn.addClickListener(clickEvent -> notifyWindowCloseListeners());

        bottomToolbar = new HLayout();
        bottomToolbar.addStyleName("vf-window-bottom-toolbar");
        bottomToolbar.addToLeft(caption);
        bottomToolbar.addToRight(closeBtn);

        layout = new DockLayout();
        layout.dockToBottom(bottomToolbar);

        setContent(layout);

        setClosable(false);
        center();

        Shortcuts.addShortcutListener(this, getCaption(), ShortcutAction.KeyCode.ESCAPE, this::notifyWindowCloseListeners);
    }

    public Component content() {
        return content;
    }

    public void content(final Component content) {
        this.content = content;
        layout.setContent(content);
    }

    protected void notifyWindowCloseListeners() {
        WindowCloseEvent windowCloseEvent = new WindowCloseEvent();
        for (EventListener<WindowCloseEvent> closeEventListener : closeEventListeners) {
            closeEventListener.onEvent(windowCloseEvent);
        }
        if (windowCloseEvent.isClose()) close();
    }

    public void show() {
        UI.getCurrent().addWindow(this);
    }

    public VWindow caption(final String s) {
        caption.setValue(s);
        return this;
    }

    public VWindow width(final String width) {
        super.setWidth(width);
        return this;
    }

    public VWindow height(final String height) {
        super.setHeight(height);
        return this;
    }

    @Override
    public void addWindowCloseListener(final EventListener<WindowCloseEvent> listener) {
        closeEventListeners.add(listener);
    }

    @Override
    public void removeWindowCloseListener(final EventListener<WindowCloseEvent> listener) {
        closeEventListeners.remove(listener);
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public DockLayout getLayout() {
        return layout;
    }

    public HLayout getBottomToolbar() {
        return bottomToolbar;
    }
}
