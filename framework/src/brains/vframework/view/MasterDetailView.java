package brains.vframework.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;
import brains.vframework.component.VWindow;

import java.io.Serializable;

public class MasterDetailView extends VerticalSplitPanel implements CrudView, Serializable {

    private final CrudView masterCrudView = new DefaultCrudView();
    private final TabSheet detailsTabs = new TabSheet();

    private final VWindow window;

    public MasterDetailView() {
        detailsTabs.setSizeFull();
        detailsTabs.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        addComponent(masterCrudView);
        addComponent(detailsTabs);

        setSplitPosition(50, Unit.PERCENTAGE, true);
        setSizeFull();

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
    public void toggleEditor(final boolean show) {
        masterCrudView.toggleEditor(show);
    }

    @Override
    public ItemFormView getItemFormView() {
        return masterCrudView.getItemFormView();
    }

    @Override
    public ListView getListView() {
        return masterCrudView.getListView();
    }

    @Override
    public ItemEditorView getItemEditorView() {
        return masterCrudView.getItemEditorView();
    }

    @Override
    public Button getToggleShowBtn() {
        return masterCrudView.getToggleShowBtn();
    }

    @Override
    public Button getNewBtn() {
        return masterCrudView.getNewBtn();
    }

    @Override
    public Button getEditBtn() {
        return masterCrudView.getEditBtn();
    }

    @Override
    public Button getDeleteBtn() {
        return masterCrudView.getDeleteBtn();
    }

    @Override
    public boolean isEditorOpened() {
        return masterCrudView.isEditorOpened();
    }

    public CrudView getMasterCrudView() {
        return masterCrudView;
    }

    public TabSheet getDetailsTabs() {
        return detailsTabs;
    }
}
