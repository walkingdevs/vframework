package brains;

import brains.bundle.HbnBundle;
import brains.bundle.HbnModule;
import brains.db.ExampleConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class Main extends Application<ExampleConfiguration> {

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
    }

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {

        final HbnBundle hibernate = new HbnBundle();
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(GuiceBundle.builder()
            .enableAutoConfig("brains.dao", "brains.rs")
            .modules(new HbnModule(hibernate))
            .build());
        bootstrap.addBundle(new VaadinBundle(GuiceApplicationServlet.class, "/foo/*"));
    }

    public static void main(String... args) throws Exception {
        new Main().run(args);
    }
}