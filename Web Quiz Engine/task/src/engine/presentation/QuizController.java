package engine.presentation;

import engine.business.*;
import engine.helper.IDGen;
import engine.database.QuizStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;

    @GetMapping("/api/quizzes/{id}")
    public Object getQuiz(@PathVariable int id) {
        Quiz q = quizService.findQuizById(id);
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
    public Object postQuizzes(@AuthenticationPrincipal UserDetailImpl details, @Valid @RequestBody Quiz quiz) {
        //quiz.setId(IDGen.getId());

        if (details == null) {
            return new ResponseEntity<>(Map.of("error", "email not valid"), HttpStatus.BAD_REQUEST);
        } else {

            User user = details.getUser();
            quiz.setUser(user);
        }

        Quiz savedQuiz = quizService.saveQuiz(quiz);

        return new ResponseEntity<>(Map.of(
                "id", savedQuiz.getId(),
                "title", savedQuiz.getTitle(),
                "text", savedQuiz.getText(),
                "options",savedQuiz.getOptions()
        ), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Object postAnswer( @PathVariable int id, @RequestBody Answer answer) {

        Quiz q = quizService.findQuizById(id);

        System.out.println("post answer: "+answer+" question" +q);
        if (q == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Integer> aList = q.getAnswer();
        List<Integer> sList = answer.getAnswer();
        Collections.sort(aList);
        Collections.sort(sList);
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
        return quizService.all();
    }

    @DeleteMapping("/api/quizzes/{id}")
    public Object deleteQuiz(@AuthenticationPrincipal UserDetailImpl details, @PathVariable int id) {
        if (details == null) {
            return new ResponseEntity<>(Map.of("error", "email not valid"), HttpStatus.BAD_REQUEST);
        } else {

            User user = details.getUser();
            Quiz quiz = quizService.findQuizById(id);

            if (quiz != null) {
                User userWhomSavedQuiz = userService.findUserById(quiz.getUser().getId());
                if (user.getEmail().equalsIgnoreCase(userWhomSavedQuiz.getEmail())) {
                    quizService.delete(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }



}
