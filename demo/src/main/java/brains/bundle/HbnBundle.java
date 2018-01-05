package brains.bundle;

import brains.db.ExampleConfiguration;
import brains.entity.Person;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class HbnBundle extends HibernateBundle<ExampleConfiguration> {
    public HbnBundle() {
        super(Person.class);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(ExampleConfiguration exampleConfiguration) {
        return exampleConfiguration.getDataSourceFactory();
    }
}
