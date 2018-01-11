package brains.vframework.embedded;

import com.google.inject.Module;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class VframeworkImpl implements Vframework {
    public VframeworkImpl(HibernateBundle hibernateBundle, Module module,  String... basePackages) {
        this.hibernateBundle = hibernateBundle;
        this.module = module;
        this.basePackages = basePackages;
    }

    private HibernateBundle hibernateBundle;
    private Module module;
    private String [] basePackages;
    @Override
    public void run(String... args){
        try {
            new Main().run(args);
            System.out.print("Server started on port 8080");
        } catch (Exception e) {
            System.out.println("Server doesn't start");
        }
    }
    private class Main extends Application<DefaultConfiguration>{
        @Override
        public void run(DefaultConfiguration defaultConfiguration, Environment environment) throws Exception {

        }

        @Override
        public void initialize(Bootstrap<DefaultConfiguration> bootstrap) {
            bootstrap.addBundle(hibernateBundle);
            bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(basePackages)
                .modules(module)
                .build());
        }
    }
}
