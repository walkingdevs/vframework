package brains.vframework.exception;

import java.io.Serializable;

public class RouteParseException extends Exception implements Serializable {

    public RouteParseException(String errorMessage) {
        super(errorMessage);
    }
}
