package engine.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Quiz {

    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @Size(min = 2)

    @Transient
    private List<String> options;
    @Transient
    private List<Integer> answer;

    @OneToMany(mappedBy = "quiz")
    private List<Options> quizOptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "quiz_answers",
            joinColumns =@JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id"
            ))
    private Set<Answers> quizAnswers = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Quiz() {
        options = new ArrayList<>(4);
        answer = new ArrayList<>(4);
        quizOptions = new ArrayList<>(4);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Options> getQuizOptions() {
        return quizOptions;
    }

    public void setQuizOptions(List<Options> quizOptions) {
        this.quizOptions = quizOptions;
    }

    public Set<Answers> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(Set<Answers> quizAnswers) {
        this.quizAnswers = quizAnswers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public void addQuizAnswer(Answers answerToAdd) {
        quizAnswers.add(answerToAdd);
    }

    public void addQuizOption(Options optionToAdd) {
        quizOptions.add(optionToAdd);
    }

    public void addAnswer(int i) {
        answer.add(i);
    }

    public void addOption(String s) {
        options.add(s);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                ", id=" + id +
                '}';
    }
}
