package deploy;

import javax.enterprise.inject.Alternative;

@Alternative
public class DeployStaging implements Deploy {
    public void run() {
    }
}