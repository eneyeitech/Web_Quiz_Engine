package engine.business;

import java.util.List;
import java.util.Set;

public class Answer {
    private Set<Integer> answer;

    public Answer(){}

    public Set<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer=" + answer +
                '}';
    }
}
