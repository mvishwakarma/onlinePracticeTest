package onlinepractice.rv.quizzz.controller.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlinepractice.rv.quizzz.controller.utils.RestVerifier;
import onlinepractice.rv.quizzz.exceptions.ModelVerificationException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.service.QuestionService;
import onlinepractice.rv.quizzz.service.QuizService;
import onlinepractice.rv.quizzz.service.accesscontrol.AccessControlService;

@Controller
public class WebQuizController {

	@Autowired
	QuizService quizService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AccessControlService<Quiz> accessControlServiceQuiz;

	@Autowired
	AccessControlService<Question> accessControlServiceQuestion;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	@RequestMapping(value = "/share-content", method = RequestMethod.GET)
	public String shareConent() {
		return "shareableTemplate";
	}
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return "about";
	}
	@RequestMapping(value = "/quizLists", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String quizLists() {
		return "quizLists";
	}
	
	@RequestMapping(value = "/practise-test", method = RequestMethod.GET)
	public String practiceTest() {
		return "practise-test";
	}
	
	@RequestMapping(value = "/payment-response", method = RequestMethod.GET)
	public String paymentResponse() {
		return "payment-response";
	}
	
	@RequestMapping(value = "/video-lectures", method = RequestMethod.GET)
	public String videoLectures() {
		return "video-lectures";
	}
	@RequestMapping(value = "/study-materials", method = RequestMethod.GET)
	public String studyMaterials() {
		return "study-materials";
	}
	@RequestMapping(value = "/study-materials/botany", method = RequestMethod.GET)
	public String studyMaterialsBotany() {
		return "study-materials/botany";
	}
	@RequestMapping(value = "/study-materials/physics", method = RequestMethod.GET)
	public String studyMaterialsPhysics() {
		return "study-materials/physics";
	}
	@RequestMapping(value = "/study-materials/chemistry", method = RequestMethod.GET)
	public String studyMaterialsChemistry() {
		return "study-materials/chemistry";
	}
	@RequestMapping(value = "/study-materials/zology", method = RequestMethod.GET)
	public String studyMaterialsZology() {
		return "study-materials/zology";
	}
	@RequestMapping(value = "/study-materials/maths", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String studyMaterialsMaths() {
		return "study-materials/maths";
	}
	@RequestMapping(value = "/free-eBooks", method = RequestMethod.GET)
	public String freeEbooks() {
		return "free-eBooks";
	} 
	@RequestMapping(value = "/packages", method = RequestMethod.GET)
	public String store() {
		return "packages";
	} 
	@RequestMapping(value = "/training-quizes", method = RequestMethod.GET)
	public String trainingQuizes() {
		return "training-quizes";
	} 
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact() {
		return "contact";
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.GET)
	public String feedback() {
		return "studentFeedback";
	}
	
	@RequestMapping(value = "/how-to-register", method = RequestMethod.GET)
	public String howtoregister() {
		return "how-to-register";
	}

//	@RequestMapping(value = "/createQuiz", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(Map<String, Object> model) {
		return "createQuiz";
	}

//	@RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result,
			Map<String, Object> model) {
		Quiz newQuiz;

		try {
			RestVerifier.verifyModelResult(result);
			newQuiz = quizService.save(quiz, user.getUser());
		} catch (ModelVerificationException e) {
			return "createQuiz";
		}

		return "redirect:/editQuiz/" + newQuiz.getId();
	}

//	@RequestMapping(value = "/editQuiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		accessControlServiceQuiz.canCurrentUserUpdateObject(quiz);

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("editQuiz");

		return mav;
	}

//	@RequestMapping(value = "/editAnswer/{question_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editAnswer(@PathVariable long question_id) {
		Question question = questionService.find(question_id);
		accessControlServiceQuestion.canCurrentUserUpdateObject(question);

		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.setViewName("editAnswers");

		return mav;
	}

	@RequestMapping(value = "/quiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")  //@PreAuthorize("permitAll")
	public ModelAndView getQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);

		accessControlServiceQuiz.canCurrentUserPlayQuiz(quiz);
		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("quizView");

		return mav;
	}

	@RequestMapping(value = "/quiz/{quiz_id}/play", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")  //@PreAuthorize("permitAll")
	public ModelAndView playQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);

		accessControlServiceQuiz.canCurrentUserPlayQuiz(quiz);
		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("playQuiz");

		return mav;
	}
	
	@RequestMapping(value = "/maintenance", method = RequestMethod.GET)
	public String maintenance() {
		return "maintenance";
	}
}
