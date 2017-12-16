package brains.vframework.spi;

public interface ItemAuditor {

    void prePersist(Object o);
    void preUpdate(Object o);
}
