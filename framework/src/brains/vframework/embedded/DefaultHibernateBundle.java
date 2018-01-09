package brains.vframework.embedded;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class DefaultHibernateBundle extends HibernateBundle<DefaultConfiguration> {
    protected DefaultHibernateBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(DefaultConfiguration defaultConfiguration) {
        return defaultConfiguration.getDataSourceFactory();
    }
}
