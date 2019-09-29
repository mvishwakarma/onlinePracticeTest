package onlinepractice.rv.quizzz.controller.rest.v1;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import onlinepractice.rv.quizzz.controller.utils.RestVerifier;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.StudentFeedback;
import onlinepractice.rv.quizzz.service.QueryService;
import onlinepractice.rv.quizzz.service.StudenFeedbackService;

@RestController
@RequestMapping(StudentFeedbackController.ROOT_MAPPING)
public class StudentFeedbackController {

	public static final String ROOT_MAPPING = "/api/feedback";
	
	private static final Logger logger = LoggerFactory.getLogger(StudentFeedbackController.class);

	@Autowired
	private StudenFeedbackService studentFeedbackService;

	

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public StudentFeedback save(@AuthenticationPrincipal AuthenticatedUser user,@RequestBody @Valid StudentFeedback studentFeedback, BindingResult result) {

		logger.debug("The studentFeedback " + studentFeedback.getName() +", MOB NO-"+studentFeedback.getMobileNo() + " is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return studentFeedbackService.save(studentFeedback, user.getUser());
	}

	
}
