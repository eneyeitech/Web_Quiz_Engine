package engine.business;

import javax.persistence.*;

@Entity
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String option;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Options() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Options{" +
                "id=" + id +
                ", option='" + option + '\'' +
                '}';
    }
}
