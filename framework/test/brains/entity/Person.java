package brains.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "Person")
public class Person {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 6)
    private String name;

    @Basic(fetch = FetchType.LAZY)
    private String lazy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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