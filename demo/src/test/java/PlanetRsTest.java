import deploy.Planet;
import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import java.net.URL;

@RunWith(Arquillian.class)
public class PlanetRsTest extends Assert {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addDefaultPackage()
            .addAsResource("META-INF/beans.xml", "META-INF/beans.xml");
    }

    @ArquillianResource
    private URL webappUrl;

    @Test
    public void testXml() throws Exception {
        Planet planet = WebClient.create(webappUrl.toURI())
                .path("rs/planet")
                .accept(MediaType.APPLICATION_XML_TYPE)
                .get(Planet.class);
        assertEquals("Uranus", planet.getName());
    }

    @Test
    public void testJson() throws Exception {
        Planet planet = WebClient.create(webappUrl.toURI())
                .path("rs/planet")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(Planet.class);
        assertEquals("Uranus", planet.getName());
    }
}