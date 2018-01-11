package brains.vframework.embedded;

public interface Vframework{
    void run(String... args) throws Exception;
    interface Builder {

        Builder basePackages(String... BasePackages);

        Builder entities(Class<?> entity, Class<?>... entities);

        Vframework build();

        static Vframework.Builder mk(){
            return new VframeworkBuilderImpl();
        }
    }
}