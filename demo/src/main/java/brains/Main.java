package brains;

import brains.vframework.embedded.VaadinBundle;
import brains.entity.Person;
import brains.vframework.embedded.Vframework;

public class Main {
    public static void main(String[] args) throws Exception {
        Vframework
            .Builder
            .mk()
            .vaadinBundle(new VaadinBundle(GuiceApplicationServlet.class, "/foo/*"))
            .basePackages("brains.dao", "brains.rs")
            .entities(Person.class)
            .build()
            .run(args);
    }
}