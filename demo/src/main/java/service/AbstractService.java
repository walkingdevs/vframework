package service;

import dao.AbstractDAO;
import io.dropwizard.util.Generics;

import javax.inject.Inject;
import java.util.List;

public class AbstractService<T> {
    @Inject
    protected AbstractDAO dao;

    protected final Class<T> entityClass;

    public AbstractService() {
        this.entityClass = (Class<T>) Generics.getTypeParameter(getClass());
    }

    public void save(final T object) {
        dao.persist(object);
    }

    public <ID> T findById(final ID id) {
        return dao.findById(entityClass, id);
    }

    public List<T> findAll() {
        return dao.findAll(entityClass);
    }

    public void delete(final T object) {
        dao.remove(object);
    }

    public <ID> void deleteById(final ID id) {
        dao.removeById(entityClass, id);
    }
}

