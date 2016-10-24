package hu.mora.model.views;

/**
 * Login screen therapist ComboBox
 */
public class LoginTherapist {
    private Integer userId;
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
