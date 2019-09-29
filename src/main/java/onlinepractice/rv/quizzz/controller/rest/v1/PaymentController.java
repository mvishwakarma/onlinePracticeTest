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

import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOptions;

import onlinepractice.rv.quizzz.controller.utils.RestVerifier;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.StudentFeedback;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;
import onlinepractice.rv.quizzz.model.support.PaymentGatewayResponse;
import onlinepractice.rv.quizzz.service.InstaMojoPaymentService;
import onlinepractice.rv.quizzz.service.QueryService;
import onlinepractice.rv.quizzz.service.StudenFeedbackService;

@RestController
@RequestMapping(PaymentController.ROOT_MAPPING)
public class PaymentController {

	public static final String ROOT_MAPPING = "/api/payment";
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private InstaMojoPaymentService instaMojoPaymentService;

	

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentOptions create(@AuthenticationPrincipal AuthenticatedUser user,@RequestBody @Valid UserOrder userOrder, BindingResult result) {

		User loggedInuser = user.getUser();
		userOrder.setCreatedBy(loggedInuser);
		logger.debug("The userorder " + loggedInuser.getUsername() +", email id -"+loggedInuser.getEmail() + ", mobile no - "+userOrder.getMobileno() +" for quiz " +  userOrder.getQuizId()+" is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return instaMojoPaymentService.createPaymentOrder(userOrder);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public UserPayment update(@AuthenticationPrincipal AuthenticatedUser user,@RequestBody PaymentGatewayResponse paymentGatewayResponse, BindingResult result) {

		User loggedInuser = user.getUser();
		
		logger.debug("The PaymentGatewayResponse " + loggedInuser.getUsername() +", email id -"+loggedInuser.getEmail() + ", txnId - "+paymentGatewayResponse.getPgtxnId() +" for id " +  paymentGatewayResponse.getPgId()+" is going to be updated");
		
		RestVerifier.verifyModelResult(result);

		return instaMojoPaymentService.update(paymentGatewayResponse);
		
	}
	
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public String procesPayment(@AuthenticationPrincipal AuthenticatedUser user,@RequestBody @Valid UserOrder userOrder, BindingResult result) throws ConnectionException, HTTPException {
		User loggedInuser = user.getUser();
		userOrder.setCreatedBy(loggedInuser);
		logger.debug("The userorder " + loggedInuser.getUsername() +", email id -"+loggedInuser.getEmail() + ", mobile no - "+userOrder.getMobileno() +" for quiz " +  userOrder.getQuizId()+" is going to be created");
		
		RestVerifier.verifyModelResult(result);

		return instaMojoPaymentService.getPaymentOrderStatus(userOrder);
		
	}	

	
}
