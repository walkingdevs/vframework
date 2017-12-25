package deploy;

import javax.enterprise.inject.Alternative;

@Alternative
public class DeployDev implements Deploy {
    public void run() {
    }
}