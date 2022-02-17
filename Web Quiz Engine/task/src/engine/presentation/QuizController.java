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
    QuizPagingAndSortingService quizPagingAndSortingService;

    @Autowired
    UserService userService;

    @Autowired
    CompletionService completionService;

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
    public Object postAnswer(@AuthenticationPrincipal UserDetailImpl details, @PathVariable int id, @RequestBody Answer answer) {

        Quiz q = quizService.findQuizById(id);

        System.out.println("post answer: "+answer+" question" +q);
        if (q == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Integer> aa = q.getAnswer();
        Set<Integer> as = new HashSet<>(aa);
        List<Integer> aList = new ArrayList<>(as);
        Set<Integer> ss = answer.getAnswer();
        Collections.sort(aList);
        List<Integer> sList = new ArrayList<>(ss);
        Collections.sort(sList);

        if(aList == null) {
            if (sList.size() == 0) {
                if (details == null) {
                    return new ResponseEntity<>(Map.of("error", "email not valid"), HttpStatus.BAD_REQUEST);
                } else {

                    User user = details.getUser();

                    Completion completion = new Completion();
                    completion.setQuiz(q);
                    completion.setUserId(user.getId());
                    completionService.save(completion);
                    System.out.println(completion);
                }

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

        if (Objects.equals(aList,sList)) {
            if (details == null) {
                return new ResponseEntity<>(Map.of("error", "email not valid"), HttpStatus.BAD_REQUEST);
            } else {

                User user = details.getUser();

                Completion completion = new Completion();
                completion.setQuiz(q);
                completion.setUserId(user.getId());
                completionService.save(completion);
                System.out.println(completion);
            }
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
    public Object getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        //return quizService.all();

        return  quizPagingAndSortingService.getAllQuiz(page, size, sort);
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

    @GetMapping("/api/quizzes/completed")
    public Object getAllCompletions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        //return quizService.all();

        return  completionService.getAllCompletion(page, size, sort);
    }



}
