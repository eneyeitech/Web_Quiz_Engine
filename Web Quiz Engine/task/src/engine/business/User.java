package engine.business;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@NotNull
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Quiz> userQuizzes = new ArrayList<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Quiz> getUserQuizzes() {
        return userQuizzes;
    }

    public void setUserQuizzes(List<Quiz> userQuizzes) {
        this.userQuizzes = userQuizzes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
