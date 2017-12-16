package brains.vframework.helpers;

import brains.vframework.event.api.PropertiesReadListener;

import java.io.*;
import java.util.Properties;

public class PropertiesHelper implements Serializable {

    public static void doOnPropertiesRead(final InputStream input, final PropertiesReadListener listener) {
        Properties properties = new Properties();
        try {
            Reader reader = new InputStreamReader(input, "UTF-8");
            properties.load(reader);
            listener.onRead(properties);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        } finally {
            if (input == null) return;
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
