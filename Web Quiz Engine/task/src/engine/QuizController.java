package engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class QuizController {

    private List<Quiz> quizzes = new ArrayList<>();

    public QuizController() {

        Quiz quiz = new Quiz();
        quiz.setTitle("The Java Logo");
        quiz.setText("What is depicted on the Java logo?");
        quiz.addOption("Robot");
        quiz.addOption("Tea leaf");
        quiz.addOption("Cup of coffee");
        quiz.addOption("Bug");
        quizzes.add(quiz);
    }

    @GetMapping("/api/quiz")
    public Object getQuiz() {
        return quizzes.get(0);
    }

    @PostMapping("/api/quiz")
    public Object postAnswer(@RequestParam int answer) {

        if (answer == 2) {
            return Map.of(
                    "success",true,
                    "feedback", "Congratulations, you're right!"
            );
        } else {
            return Map.of(
                    "success",false,
                    "feedback", "Wrong answer! Please, try again."
            );
        }

    }

}
