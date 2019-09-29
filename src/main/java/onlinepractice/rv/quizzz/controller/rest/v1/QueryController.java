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
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.InstaMojoPaymentService;
import onlinepractice.rv.quizzz.service.QueryService;

@RestController
@RequestMapping(QueryController.ROOT_MAPPING)
public class QueryController {

	public static final String ROOT_MAPPING = "/api/query";
	
	private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

	@Autowired
	private QueryService queryService;
	
	@Autowired
	private InstaMojoPaymentService instaMojoPaymentService ;

	
	@RequestMapping(value = "/anonymous", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.CREATED)
	public Query saveNew( @RequestBody @Valid Query query, BindingResult result) {

		logger.debug("The query " + query.getName()+", MOB NO-"+query.getPhoneNo() + " is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return queryService.save(query, null);
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public Query save(@AuthenticationPrincipal AuthenticatedUser user,@RequestBody Query query, BindingResult result) {

		User loggedInuser = user.getUser();
		logger.debug("The userorder " + loggedInuser.getUsername() +", email id -"+loggedInuser.getEmail() + " is going to be created");
		
		RestVerifier.verifyModelResult(result);
		
		
		return queryService.save(query, loggedInuser);
	}

	
}
