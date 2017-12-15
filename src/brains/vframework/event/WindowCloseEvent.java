package brains.vframework.event;

import java.io.Serializable;

public class WindowCloseEvent implements Serializable {

    private boolean close = true;

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}