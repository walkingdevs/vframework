import com.google.inject.servlet.ServletModule;
import view.Service1;
import view.ServiceImpl;

public class SModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(Service1.class).to(ServiceImpl.class);

    }
}
