package domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Person extends AbstractEntity{

    @Size(min = 6)
    private String name;

    @Basic(fetch = FetchType.LAZY)
    private String lazy;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLazy(String lazy) {
        this.lazy = lazy;
    }

    public String getLazy() {
        return lazy;
    }
}

