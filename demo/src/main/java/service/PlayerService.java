package service;

import com.google.common.collect.ImmutableMap;
import entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerService extends AbstractService<Person> {

    public List<Person> findPlayerByName(final Optional<String> name) {
        if (name.isPresent()) {
            return dao.find(entityClass, "Person.findByName", ImmutableMap.of("name", name.get()));
        } else {
            return new ArrayList<>(0);
        }
    }
}
