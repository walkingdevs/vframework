package brains.vframework.embedded;

import com.google.inject.Module;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class VframeworkImpl implements Vframework {
    public VframeworkImpl(HibernateBundle hibernateBundle, Module module, VaadinBundle vaadinBundle, String... basePackages) {
        this.hibernateBundle = hibernateBundle;
        this.module = module;
        this.vaadinBundle = vaadinBundle;
        this.basePackages = basePackages;
    }

    private HibernateBundle hibernateBundle;
    private Module module;
    private VaadinBundle vaadinBundle;
    private String [] basePackages;
    @Override
    public void run(String... args){
        try {
            new Main().run(args);
            System.out.print("Server started on port 8080");
        } catch (Exception e) {
            e.printStackTrace();
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
            bootstrap.addBundle(vaadinBundle);
        }
    }
}
