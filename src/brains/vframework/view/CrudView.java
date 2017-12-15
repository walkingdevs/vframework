package brains.vframework.view;

import com.vaadin.ui.Button;
import brains.vframework.view.api.View;

public interface CrudView extends View {

    void toggleEditor(boolean show);
    ItemFormView getItemFormView();
    ListView getListView();
    ItemEditorView getItemEditorView();
    Button getToggleShowBtn();
    Button getNewBtn();
    Button getEditBtn();
    Button getDeleteBtn();
    boolean isEditorOpened();
}
