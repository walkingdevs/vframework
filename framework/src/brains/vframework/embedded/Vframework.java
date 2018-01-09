package brains.vframework.embedded;

import io.dropwizard.Bundle;

public interface Vframework{
    void run(String... args) throws Exception;
    interface Builder {

        Builder basePackages(String... BasePackages);

        Builder vaadinBundle(Bundle vaadinBundle);

        Builder entities(Class<?> entity, Class<?>... entities);

        Vframework build();

        static Vframework.Builder mk(){
            return new VframeworkBuilderImpl();
        }
    }
}