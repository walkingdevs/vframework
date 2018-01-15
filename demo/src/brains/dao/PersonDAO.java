package brains.dao;

import com.google.inject.Inject;
import brains.entity.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import ru.vyarus.dropwizard.guice.module.installer.feature.eager.EagerSingleton;

import java.util.List;

@EagerSingleton
public class PersonDAO extends AbstractDAO<Person> {

    @Inject
    public PersonDAO(SessionFactory factory) {
        super(factory);
    }
    public Person findById(Long id) {
        return get(id);
    }

    public Person create(Person person) {
        return persist(person);
    }

    public List<Person> findAll() {
        return list(namedQuery("com.example.helloworld.core.Person.findAll"));
    }

    public Long create(String name, String lazy) {
        Person person = new Person();
        person.setName(name);
        person.setLazy(lazy);
        return persist(person).getId();
    }
}