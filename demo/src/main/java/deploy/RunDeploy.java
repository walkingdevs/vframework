package deploy;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class RunDeploy {
    @Inject
    private Deploy deploy;

    @PostConstruct
    public void run() {
        System.out.println("# ------------------------------------------------------------");
        System.out.println("# " + deploy.getClass().getSimpleName());
        System.out.println("# ------------------------------------------------------------");

        deploy.run();
    }
}