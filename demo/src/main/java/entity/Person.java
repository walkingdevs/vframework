package entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name ORDER BY p.id")
})
@Table(name = "PERSON")
public class Person extends AbstractEntity {
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "NAME", length = 45)
    private String name;

    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "LOGIN", length = 45)
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

