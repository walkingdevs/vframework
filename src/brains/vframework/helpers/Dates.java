package brains.vframework.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Dates {

    public static int calcAge(final Date dob) {
        Date today = new Date();

        int age = today.getYear() - dob.getYear();

        if (today.getMonth() < dob.getMonth()) {
            age--;
        } else if (today.getMonth() == dob.getMonth() && today.getDay() < dob.getDay()) {
            age--;
        }

        return age;
    }

    public static String toDateFormat(final Date date) {
        SimpleDateFormat format;
        if (date.getHours() == 0 && date.getMinutes() == 0) {
            // TODO dont use VaadinSession
            format = new SimpleDateFormat("dd-MMM-yyyy", Locale.forLanguageTag("ru-RU"));
        } else {
            // TODO dont use VaadinSession
            format = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.forLanguageTag("ru-RU"));
        }
        return format.format(date);
    }
}
