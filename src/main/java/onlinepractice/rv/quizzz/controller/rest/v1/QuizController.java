package onlinepractice.rv.quizzz.controller.rest.v1;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import onlinepractice.rv.quizzz.controller.utils.RestVerifier;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.support.Response;
import onlinepractice.rv.quizzz.model.support.Result;
import onlinepractice.rv.quizzz.service.QuestionService;
import onlinepractice.rv.quizzz.service.QuizService;

@RestController
@RequestMapping(QuizController.ROOT_MAPPING)
public class QuizController {

	public static final String ROOT_MAPPING = "/api/quizzes";
	
	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuestionService questionService;

	//@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> findAll(Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean published) {
		
		/*if (published) {
			return quizService.findAllPublished(pageable);
		} else {
			return quizService.findAll(pageable);
		}*/
		return quizService.findAllPublished(pageable);
	}

	
	// this will show free quiz and enrolled quiz for user 
	@RequestMapping(value = "/opted", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> findAllEligible(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean published,
			@RequestParam(required = false, defaultValue = "false") String tags) {
		User loggedInuser = authenticatedUser.getUser();
		 Page<Quiz> returnlist = quizService.findAllPublishedForUser(loggedInuser.getUser(),pageable);
		 logger.debug("size of non fileter quizzes = "+returnlist.getSize());
		 return quizService.filterByTag(returnlist, tags);
	}

	
	// this will show available/paid quiz for user 
	@RequestMapping(value = "/open", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> findAllAvailableForUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean published,
			@RequestParam(required = false, defaultValue = "false") String tags) {
		User loggedInuser = authenticatedUser.getUser();
		return quizService.findAllAvailableForUser(loggedInuser.getUser(),pageable);
	}

	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Quiz> searchAll(Pageable pageable, @RequestParam(required = true) String filter,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		return quizService.search(filter, pageable);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public Quiz save(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result) {

		logger.debug("The Quiz " + quiz.getName() + " is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return quizService.save(quiz, user.getUser());
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Quiz find(@PathVariable Long quiz_id) {

		return quizService.find(quiz_id);
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Quiz update(@PathVariable Long quiz_id, @Valid Quiz quiz, BindingResult result) {

		RestVerifier.verifyModelResult(result);

		quiz.setId(quiz_id);
		return quizService.update(quiz);
	}

	@RequestMapping(value = "/{quiz_id}", method = RequestMethod.DELETE)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		quizService.delete(quiz);
	}

	@RequestMapping(value = "/{quiz_id}/questions", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public List<Question> findQuestions(@PathVariable Long quiz_id,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		Quiz quiz = quizService.find(quiz_id);

		if (onlyValid) {
			return questionService.findValidQuestionsByQuiz(quiz);
		} else {
			return questionService.findQuestionsByQuiz(quiz);
		}

	}

	@RequestMapping(value = "/{quiz_id}/publish", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public void publishQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		quizService.publishQuiz(quiz);
	}

	@RequestMapping(value = "/{quiz_id}/submitAnswers", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public Result playQuiz(@AuthenticationPrincipal AuthenticatedUser authenticatedUser, @PathVariable long quiz_id, @RequestBody List<Response> answersBundle) {
		Quiz quiz = quizService.find(quiz_id);
		User loggedInuser = authenticatedUser.getUser();
		boolean persistCandidateResponse = true;
		return quizService.checkAnswers(loggedInuser.getUser(),quiz, answersBundle,persistCandidateResponse);
		
	}

}
