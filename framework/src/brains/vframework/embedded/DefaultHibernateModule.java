package brains.vframework.embedded;

import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;

public class DefaultHibernateModule extends AbstractModule {

    public DefaultHibernateModule(DefaultHibernateBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(bundle.getSessionFactory());
    }

    private final DefaultHibernateBundle bundle;
}
