package brains;

import brains.entity.Person;
import brains.vframework.embedded.VaadinBundle;
import brains.vframework.embedded.Vframework;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class ButtonClickTest
    extends TestBenchTestCase
    {
    @Before
    public void setUp() throws Exception {
        setDriver(new ChromeDriver());
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
    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }
    @Test
    public void buttonTest() throws Exception {
        getDriver().get("http://localhost:3000/foo");
        Assert.assertTrue($(ButtonElement.class).exists());
        Assert.assertTrue($(LabelElement.class).exists());
        Assert.assertEquals("Value", $(LabelElement.class).first().getText());
        $(ButtonElement.class).caption("Change").first().click();
        Assert.assertEquals("Changed", $(LabelElement.class).first().getText());
    }
}
