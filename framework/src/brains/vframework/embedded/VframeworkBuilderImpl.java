package brains.vframework.embedded;

import brains.vframework.event.api.ActionListener;
import com.google.inject.Module;
import io.dropwizard.hibernate.HibernateBundle;

public class VframeworkBuilderImpl implements Vframework.Builder{

    public Vframework.Builder basePackages(String... basePackages) {
        this.basePackages = basePackages;
        return this;
    }

    public Vframework.Builder entities(Class<?> entity, Class<?>... entities) {
        this.entity = entity;
        this.entities = entities;
        return this;
    }

    public Vframework.Builder vaadinBundle(VaadinBundle vaadinBundle) {
        this.vaadinBundle = vaadinBundle;
        return this;
    }

    public Vframework.Builder success(ActionListener action) {
        this.action = action;
        return this;
    }

    public VframeworkImpl build() {
        hibernateBundle = new DefaultHibernateBundle(this.entity, this.entities);
        module = new DefaultHibernateModule(hibernateBundle);
        return new VframeworkImpl(vaadinBundle, hibernateBundle, module, this.action, this.basePackages);
    }

    private Class<?> entity;
    private Class<?>[] entities;
    private HibernateBundle<DefaultConfiguration> hibernateBundle;
    private VaadinBundle vaadinBundle;
    private Module module;
    private String [] basePackages;
    private ActionListener action;
}
