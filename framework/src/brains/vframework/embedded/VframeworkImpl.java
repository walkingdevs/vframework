package brains.vframework.embedded;

import brains.vframework.event.api.ActionListener;
import com.google.inject.Module;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class VframeworkImpl implements Vframework {

    public VframeworkImpl(VaadinBundle vaadinBundle, HibernateBundle hibernateBundle, Module module, ActionListener action, String... basePackages) {
        this.hibernateBundle = hibernateBundle;
        this.module = module;
        this.basePackages = basePackages;
        this.vaadinBundle = vaadinBundle;
        this.action = action;
    }

    private final ActionListener action;
    private final VaadinBundle vaadinBundle;
    private final HibernateBundle hibernateBundle;
    private final Module module;
    private final String [] basePackages;
    @Override
    public void run(String... args) throws Exception {
            new Main().run(args);
    }
    private class Main extends Application<DefaultConfiguration>{
        @Override
        public void run(DefaultConfiguration defaultConfiguration, Environment environment) throws Exception {
            action.onAction();
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
