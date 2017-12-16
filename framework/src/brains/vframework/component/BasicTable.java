package brains.vframework.component;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BasicTable extends Table implements Serializable {

    public BasicTable() {
        setSizeFull();
        addStyleName("compact small borderless");
        setSelectable(true);
    }

    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof Date) {
            ZonedDateTime date = ((Date) v).toInstant().atZone(ZoneId.systemDefault());
            return date.format(DateTimeFormatter.ofPattern("dd-MM-yy [HH:mm]"));
        }
        return super.formatPropertyValue(rowId, colId, property);
    }
}
