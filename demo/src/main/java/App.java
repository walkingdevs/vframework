import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceFilter;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resource.HomeResource;
import resource.PlayerResource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Properties;

public class App extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new VaadinBundle(GuiceApplicationServlet.class, "/foo/*"));
    }

//    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
//    public static class Servlet extends VaadinServlet {
//        // empty
//    }


    @Override
    public void run(Config conf, Environment env) throws Exception {
        final Injector injector = Guice.createInjector(new AppModule(conf, env), createJpaModule());
        env.servlets().addFilter("GuiceFilter", new GuiceFilter())
            .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/foo/*");
        env.servlets().addServletListeners(new BarContextListener());
        env.servlets().addFilter("persistFilter", injector.getInstance(PersistFilter.class));
        env.jersey().register(injector.getInstance(HomeResource.class));
        env.jersey().register(injector.getInstance(PlayerResource.class));
    }

    private JpaPersistModule createJpaModule() {
        final Properties properties = new Properties();
        properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/postgres");
        properties.put("javax.persistence.jdbc.user", "postgres");
        properties.put("javax.persistence.jdbc.password", "");

        final JpaPersistModule jpaModule = new JpaPersistModule("DefaultUnit");
        jpaModule.properties(properties);

        return jpaModule;
    }
}
