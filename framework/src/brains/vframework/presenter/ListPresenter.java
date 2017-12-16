package brains.vframework.presenter;

import brains.vframework.VMessages;
import brains.vframework.annotation.Item;
import brains.vframework.annotation.Property;
import brains.vframework.event.api.ActionListener;
import brains.vframework.event.source.ContainerRefreshedEventSource;
import brains.vframework.helpers.Reflections;
import brains.vframework.helpers.Shortcuts;
import brains.vframework.view.ListView;
import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Like;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ListPresenter<ITEM, VIEW extends ListView> extends BasicListPresenter<ITEM, VIEW> implements ContainerRefreshedEventSource, Serializable {

    private final List<Container.Filter> filters = new ArrayList<>();
    private Container.Filter searchFilter;
    private String searchFieldName;

    private List<ActionListener> containerRefreshedEventListeners = new ArrayList<>();

    private ShortcutListener shortcutListener;

    @Override
    public void view(final VIEW view) {
        super.view(view);

        view.searchTxt.addFocusListener(focusEvent -> {
            shortcutListener = Shortcuts.addShortcutListener(view.searchTxt, "Search", ShortcutAction.KeyCode.ENTER, this::applyFilter);
        });
        view.searchTxt.addBlurListener(blurEvent -> {
            view.searchTxt.removeShortcutListener(shortcutListener);
        });

        view.refreshBtn.addClickListener(clickEvent -> {
            onReload();
            notifyContainerRefreshedEventListeners();
        });
    }

    private void applyFilter() {
        container().removeContainerFilter(searchFilter);

        if (StringUtils.isEmpty(view.searchTxt.getValue())) {
            onReload();
            return;
        }

        final Class<?> type;
        if (searchFieldName.contains(".")) {
            type = String.class;
        } else {
            type = Reflections.getField(container().getEntityClass(), searchFieldName).getType();
        }
        if (Long.class == type || Integer.class == type || BigInteger.class == type) {
            searchFilter = new Compare.Equal(searchFieldName, view.searchTxt.getValue());
        } else if (BigDecimal.class == type || Double.class == type || Float.class == type) {
            Integer value = Integer.valueOf(view.searchTxt.getValue());
            searchFilter = new Compare.Equal(searchFieldName, value);
        } else if (String.class == type) {
            searchFilter = new Like(searchFieldName, "%" + view.searchTxt.getValue()+"%", false);
        } else {
            throw new NotImplementedException("Can't filter by " + type);
        }

        container().addContainerFilter(searchFilter);
        container().applyFilters();
        onReload();
    }

    private void notifyContainerRefreshedEventListeners() {
        containerRefreshedEventListeners.forEach(ActionListener::onAction);
    }

    @Override
    public void container(final EntityContainer<ITEM> container) {
        super.container(container);

        searchFieldName = Item.SearchProperties.get(container.getEntityClass());

        if (searchFieldName.contains(".")) {
            view.searchTxt.setInputPrompt("Поле для поиска: " + VMessages.getText(searchFieldName.substring(0, searchFieldName.indexOf("."))) + " - " + VMessages.getText(searchFieldName.substring(searchFieldName.indexOf(".") + 1)));
        }
        else {
            view.searchTxt.setInputPrompt("Поле для поиска: " + VMessages.getText(searchFieldName));
        }

        List<String> searchFields = Property.Searchables.namesAll(container.getEntityClass());
        view.filterFields.addItems(searchFields);
        view.filterFields.setPageLength(searchFields.size());
        view.filterFields.getItemIds().forEach(o -> {
            String caption = o.toString();
            if (caption.contains(".")) view.filterFields.setItemCaption(o, VMessages.getText(caption.substring(0, caption.indexOf("."))) + " - " + VMessages.getText(caption.substring(caption.indexOf(".") + 1)));
            else view.filterFields.setItemCaption(o, VMessages.getText(caption));
        });
        view.filterFields.addValueChangeListener(valueChangeEvent -> {
            searchFieldName = view.filterFields.getValue().toString();
            view.searchTxt.setInputPrompt("Поле для поиска: " + view.filterFields.getItemCaption(view.filterFields.getValue()));
        });
    }

    @Override
    public void addContainerRefreshedEventListener(final ActionListener listener) {
        containerRefreshedEventListeners.add(listener);
    }

    @Override
    public void removeContainerRefreshedEventListener(final ActionListener listener) {
        containerRefreshedEventListeners.remove(listener);
    }
}
