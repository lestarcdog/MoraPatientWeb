package hu.mora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "THERAPISTS")
public class LoginTherapist {

    @Id
    @Column(name = "ID")
    private Integer userId;

    @Column(name = "NAME")
    private String name;

    public LoginTherapist() {
    }

    public LoginTherapist(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
