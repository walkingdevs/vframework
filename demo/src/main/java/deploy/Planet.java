package deploy;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Planet {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}