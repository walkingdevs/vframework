package brains.vframework.component;

import com.vaadin.data.Property;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BasicPagedTable extends VTable implements Serializable {

    public BasicPagedTable() {
        addStyleName("compact small borderless bg-back");
        setSelectable(true);
        setBuffered(false);
    }

    //TODO
    @Override
    protected String formatPropertyValue(Object rowId, Object colId, Property property) {
        Object v = property.getValue();
        if (v instanceof Date) {
            ZonedDateTime date = ((Date) v).toInstant().atZone(ZoneId.systemDefault());
            return date.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
        }
        return super.formatPropertyValue(rowId, colId, property);
    }
}
