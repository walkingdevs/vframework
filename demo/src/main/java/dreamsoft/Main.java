package dreamsoft;

import dreamsoft.entity.Person;
import brains.vframework.embedded.Vframework;

public class Main {
    public static void main(String[] args) throws Exception {
        Vframework
            .Builder
            .mk()
            .vaadinBundle(new VaadinBundle(GuiceApplicationServlet.class, "/foo/*"))
            .basePackages("dreamsoft.dao", "dreamsoft.rs")
            .entities(Person.class)
            .build()
            .run(args);
    }
}