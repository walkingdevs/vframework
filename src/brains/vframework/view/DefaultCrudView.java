package brains.vframework.view;

import brains.vframework.VMessages;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import brains.vframework.component.VWindow;
import brains.vframework.view.api.View;

import java.io.Serializable;

public class DefaultCrudView extends HorizontalSplitPanel implements View, CrudView, Serializable {

    private final ListView listView = new ListView();
    private final ItemFormView itemFormView = new ItemFormView();
    private final ItemEditorView itemEditorView = new ItemEditorView();

    private final Button toggleShowBtn;
    private final Button newBtn;
    private final Button editBtn;
    private final Button deleteBtn;

    private final VWindow window;

    private boolean editorOpened = false;

    public DefaultCrudView() {

        newBtn = new Button(VMessages.getText("new"), FontAwesome.PLUS_CIRCLE);
        newBtn.addStyleName("small borderless");

        editBtn = new Button(VMessages.getText("edit"), FontAwesome.EDIT);
        editBtn.addStyleName("small borderless");

        deleteBtn = new Button(VMessages.getText("delete"), FontAwesome.TIMES_CIRCLE);
        deleteBtn.addStyleName("small borderless");

        toggleShowBtn = new Button(VMessages.getText("quick_editor"), FontAwesome.ANGLE_DOUBLE_RIGHT);
        toggleShowBtn.addStyleName("small borderless");

        listView.topToolbar.addToLeft(newBtn);
        listView.topToolbar.addToLeft(editBtn);
        listView.topToolbar.addToLeft(deleteBtn);

        listView.topToolbar.addToRight(toggleShowBtn);

        setSizeFull();
        addComponent(listView);
        addComponent(itemEditorView);
        setSplitPosition(0, Unit.PIXELS, true);

        addStyleName("entity-crud-view");

        listView.topToolbar2.removeStyleName("toolbar-default");

        window = new VWindow(this);
        window.width("98%").height("98%");
        window.setModal(true);
        window.setResizable(false);
    }

    @Override
    public VWindow getWindow() {
        return window;
    }

    @Override
    public void toggleEditor(boolean show) {
        if (editorOpened && !show) {
            setSplitPosition(0, Unit.PIXELS, true);
            toggleShowBtn.setIcon(FontAwesome.ANGLE_DOUBLE_LEFT);
            editorOpened = false;
        } else {
            setSplitPosition(340, Unit.PIXELS, true);
            toggleShowBtn.setIcon(FontAwesome.ANGLE_DOUBLE_RIGHT);
            editorOpened = true;
        }
    }

    @Override
    public ItemFormView getItemFormView() {
        return itemFormView;
    }

    @Override
    public ListView getListView() {
        return listView;
    }

    @Override
    public ItemEditorView getItemEditorView() {
        return itemEditorView;
    }

    @Override
    public Button getToggleShowBtn() {
        return toggleShowBtn;
    }

    @Override
    public Button getNewBtn() {
        return newBtn;
    }

    @Override
    public Button getEditBtn() {
        return editBtn;
    }

    @Override
    public Button getDeleteBtn() {
        return deleteBtn;
    }

    @Override
    public boolean isEditorOpened() {
        return editorOpened;
    }
}
