package engine.database;

import engine.business.Quiz;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QuizStore {

    private Map<Long, Quiz> quizzes = new ConcurrentHashMap<>();;

    {
       /** Quiz quiz = new Quiz();
        quiz.setId(IDGen.getId());
        quiz.setTitle("The Java Logo");
        quiz.setText("What is depicted on the Java logo?");
        quiz.addOption("Robot");
        quiz.addOption("Tea leaf");
        quiz.addOption("Cup of coffee");
        quiz.addOption("Bug");
        quiz.setAnswer(2);
        quizzes.put(quiz.getId(), quiz);*/
    }

    public void addQuiz(Quiz quiz) {
        System.out.println(quiz);
        quizzes.put(quiz.getId(), quiz);
    }

    public Quiz getQuiz(long id) {
        System.out.println(id);
        return quizzes.get(id);
    }

    public Collection<Quiz> getAllQuiz() {
        Collection<Quiz> list =  quizzes.values();
        return list;
    }
}
