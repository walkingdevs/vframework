package brains.vframework.embedded;

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

    public Vframework build() {
        hibernateBundle = new DefaultHibernateBundle(this.entity, this.entities);
        module = new DefaultHibernateModule(hibernateBundle);
        return new VframeworkImpl(hibernateBundle, module,  this.basePackages);
    }

    private Class<?> entity;
    private Class<?>[] entities;
    private HibernateBundle<DefaultConfiguration> hibernateBundle;
    private Module module;
    private String [] basePackages;
}
