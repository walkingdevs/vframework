package brains.vframework.embedded;

import brains.vframework.event.api.ActionListener;

public interface Vframework{
    void run(String... args) throws Exception;
    interface Builder {

        Builder basePackages(String... BasePackages);

        Builder entities(Class<?> entity, Class<?>... entities);

        Builder vaadinBundle(VaadinBundle vaadinBundle);

        Builder success(ActionListener action);

        VframeworkImpl build();

        static Vframework.Builder mk(){
            return new VframeworkBuilderImpl();
        }
    }
}