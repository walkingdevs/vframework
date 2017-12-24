import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import filer.LazyHibernateServletFilter;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main extends Application<ExampleConfiguration> {

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
        // empty
        environment
            .servlets()
            .addFilter("LazyHibernateServletFilter", new LazyHibernateServletFilter())
            .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/foo/*");
        final PersonDAO dao = new PersonDAO(hibernate.getSessionFactory());
        environment.jersey().register(new UserResource(dao));
    }

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        bootstrap.addBundle(new VaadinBundle(Servlet.class, "/foo/*"));
        bootstrap.addBundle(hibernate);
    }

    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
        // empty
    }

    public static void main(String... args) throws Exception {
        new Main().run(args);
    }

    private final HibernateBundle<ExampleConfiguration> hibernate = new HibernateBundle<ExampleConfiguration>(Person.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}