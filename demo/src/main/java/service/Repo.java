package service;

import java.util.List;

public interface Repo<T> {
    void save(final T object);

    <ID> T findById(final ID id);

    List<T> findAll();

    void delete(final T object);

    <ID> void deleteById(final ID id);
}
