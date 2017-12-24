import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import io.dropwizard.Application;
import io.dropwizard.Bundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.session.SessionHandler;
import resource.HomeResource;
import resource.PlayerResource;

import java.util.Properties;

public class Main extends Application<Config> {

    @Override
    public void run(Config conf, Environment env) throws Exception {

        final Injector injector = Guice.createInjector(new AppModule(conf, env), createJpaModule());
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

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new Bundle() {
            @Override
            public void initialize(Bootstrap<?> bootstrap) {
                bootstrap.addBundle(new AssetsBundle("/VAADIN", "/VAADIN", null, "vaadin"));
            }

            @Override
            public void run(Environment environment) {
                environment.servlets().setSessionHandler(new SessionHandler());
                environment.getApplicationContext().addServlet(Servlet.class, "/foo/*");
            }
        });
    }

    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
        // empty
    }

    public static void main(String... args) throws Exception {
        new Main().run(args);
    }
}