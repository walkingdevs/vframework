package deploy;

import javax.enterprise.inject.Alternative;

@Alternative
public class DeployProd implements Deploy {
    public void run() {
    }
}