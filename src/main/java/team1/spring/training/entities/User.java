package team1.spring.training.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    @Column(name = "id")
    private long id;

    @NotNull
    @NotEmpty
    @JsonProperty("name")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
