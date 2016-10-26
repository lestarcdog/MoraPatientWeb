package hu.mora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "THERAPISTS")
public class LoginTherapist {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public LoginTherapist() {
    }

    public LoginTherapist(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LoginTherapist{" +
                "userId=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
