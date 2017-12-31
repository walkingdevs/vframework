import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resource.HomeResource;
import resource.PlayerResource;

import java.util.Properties;

public class App extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(Config conf, Environment env) throws Exception {
        final Injector injector = Guice.createInjector(new AppModule(conf, env), createJpaModule(), new SModule());
        env.jersey().register(injector.getInstance(HomeResource.class));
        env.jersey().register(injector.getInstance(PlayerResource.class));
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new VaadinBundle(GuiceApplicationServlet.class, "/foo/*"));
        bootstrap.addBundle(new AssetsBundle("/VAADIN", "/VAADIN", null));
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
