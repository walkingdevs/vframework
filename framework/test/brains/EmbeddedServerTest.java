package brains;

import brains.entity.Person;
import brains.vframework.embedded.VaadinBundle;
import brains.vframework.embedded.Vframework;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import walkingdevs.http11.ReqBuilder;

public class EmbeddedServerTest extends Assert{

    private void start() throws Exception {
        Vframework
            .Builder
            .mk()
            .basePackages("brains.dao", "brains.rs")
            .entities(Person.class)
            .vaadinBundle(new VaadinBundle(ApplicationServlet.class, "/foo/*"))
            .success(()->{
            })
            .build()
            .run("server", "test.yml");
    }

    @Test
    public void test() throws Exception {
        start();
        assertEquals(200L, ReqBuilder.GET("http://localhost:3000/foo").build().send().status());
        assertEquals(200L, ReqBuilder.GET("http://localhost:3000/brains/rs/post/olzhas").build().send().status());
        JSONAssert.assertEquals("{\"id\":1,\"name\":\"olzhas\",\"lazy\":\"olzhas\"}",
            ReqBuilder.GET("http://localhost:3000/brains/rs/1").build().send().body().text(), false);
    }
}
