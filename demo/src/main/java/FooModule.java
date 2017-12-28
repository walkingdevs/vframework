import com.google.inject.servlet.ServletModule;

public class FooModule extends ServletModule{
    @Override
    protected void configureServlets() {
        serve("/foo/*").with(GuiceApplicationServlet.class);
    }
}
