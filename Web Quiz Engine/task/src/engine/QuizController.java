package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Supplier;

@RestController
public class QuizController {

    @Autowired
    QuizStore quizStore;

    @GetMapping("/api/quizzes/{id}")
    public Object getQuiz(@PathVariable int id) {
        Quiz q = quizStore.getQuiz(id);
        if (q == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Map.of(
                "id", q.getId(),
                "title", q.getTitle(),
                "text", q.getText(),
                "options",q.getOptions()
        ), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public Object postQuizzes(@Valid @RequestBody Quiz quiz) {
        quiz.setId(IDGen.getId());
        quizStore.addQuiz(quiz);

        return new ResponseEntity<>(Map.of(
                "id", quiz.getId(),
                "title", quiz.getTitle(),
                "text", quiz.getText(),
                "options",quiz.getOptions()
        ), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Object postAnswer(@PathVariable int id, @RequestBody Answer answer) {
        Quiz q = quizStore.getQuiz(id);
        System.out.println("post answer: "+answer+" question" +q);
        if (q == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Integer> aList = q.getAnswer();
        List<Integer> sList = answer.getAnswer();

        if(aList == null) {
            if (sList.size() == 0) {
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

        if (aList.equals(sList)) {

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

    @GetMapping("/api/quizzes")
    public Object getAllQuizzes() {
        return quizStore.getAllQuiz();
    }



}
