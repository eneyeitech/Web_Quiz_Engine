package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Quiz {

    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @Size(min = 2)
    private List<String> options;
    private List<Integer> answer;
    private long id;

    public Quiz() {
        options = new ArrayList<>(4);
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

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean addOption(String opt) {
        if (options.size() > 4) {
            return false;
        }
        return options.add(opt);
    }

    public boolean addAnswer(int ans) {
        if (answer.size() > 4) {
            return false;
        }
        return answer.add(ans);
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
