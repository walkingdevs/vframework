package brains;

import brains.entity.Person;
import brains.vframework.embedded.Vframework;

public class Main {
    public static void main(String[] args) throws Exception {
        Vframework
            .Builder
            .mk()
            .basePackages("brains.dao", "brains.rs")
            .entities(Person.class)
            .build()
            .run(args);
    }
}