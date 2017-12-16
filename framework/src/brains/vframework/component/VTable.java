package brains.vframework.component;

import brains.vframework.VMessages;
import brains.vframework.helpers.Dates;
import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VTable extends PagedTable implements Serializable {

    public VTable() {
        setSizeFull();
        addStyleName("compact small borderless");
        setSelectable(true);

        setRowGenerator((table, itemId) -> {
            final GeneratedRow row = new GeneratedRow();
            row.setHtmlContentAllowed(true);

            List<String> cellCaptions = new ArrayList<>();
            for (final Object propertyId : getVisibleColumns()) {
                Property property = getContainerDataSource().getItem(itemId).getItemProperty(propertyId);
                if (property == null || property.getValue() == null) {
                    cellCaptions.add("");
                } else if (property.getValue() instanceof Boolean) {
                    if ((Boolean) property.getValue()) {
                        cellCaptions.add("<input type='checkbox' disabled checked />");
                    } else {
                        cellCaptions.add("<input type='checkbox' disabled />");
                    }
                } else if (property.getValue() instanceof Date) {
                    cellCaptions.add(Dates.toDateFormat((Date) property.getValue()));
                } else if (Enum.class.isAssignableFrom(property.getValue().getClass())) {
                    cellCaptions.add(VMessages.getText(property.getValue().toString()));
                } else if (property.getValue() != null) {
                    cellCaptions.add(property.getValue().toString());
                } else {
                    cellCaptions.add("");
                }
            }
            String[] a = new String[cellCaptions.size()];
            row.setText(cellCaptions.toArray(a));
            return row;
        });
    }
}
