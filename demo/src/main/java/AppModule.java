import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import dao.Dao;
import dao.DaoImpl;
import io.dropwizard.setup.Environment;
import service.PlayerService;
import service.ScoreService;
import view.Service1;
import view.ServiceImpl;

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
        bind(Dao.class).to(DaoImpl.class).in(Singleton.class);
        bind(PlayerService.class).in(Singleton.class);
        bind(ScoreService.class).in(Singleton.class);
    }
}
