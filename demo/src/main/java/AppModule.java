import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import dao.AbstractDAO;
import dao.AbstractDAOImpl;
import io.dropwizard.setup.Environment;
import service.PlayerService;
import service.ScoreService;

public class AppModule extends AbstractModule {

    final Config configuration;
    final Environment environment;

    public AppModule(final Config configuration, final Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(Config.class).toInstance(configuration);
        bind(Environment.class).toInstance(environment);
        bind(AbstractDAO.class).to(AbstractDAOImpl.class).in(Singleton.class);
        bind(PlayerService.class).in(Singleton.class);
        bind(ScoreService.class).in(Singleton.class);
    }
}