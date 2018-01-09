package brains.vframework.embedded;

import com.google.inject.AbstractModule;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class DefaultHibernateModule extends AbstractModule {

    public DefaultHibernateModule(HibernateBundle<DefaultConfiguration> bundle) {
        this.bundle = bundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(bundle.getSessionFactory());
    }

    private final HibernateBundle bundle;
}
