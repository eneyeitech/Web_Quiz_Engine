package engine.business;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int answer;

    public Answers() {
    }

    @ManyToMany(mappedBy = "quizAnswers", fetch = FetchType.EAGER )
    private Set<Quiz> quizzes;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "id=" + id +
                ", answer=" + answer +
                '}';
    }
}
