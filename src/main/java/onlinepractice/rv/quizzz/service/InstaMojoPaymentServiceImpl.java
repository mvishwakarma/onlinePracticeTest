package onlinepractice.rv.quizzz.service;

import static onlinepractice.rv.quizzz.constant.PaymentGatewayConstants.*;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOptions;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.model.Pack;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;
import onlinepractice.rv.quizzz.model.support.PaymentGatewayResponse;
import onlinepractice.rv.quizzz.repository.ContactQueryRepository;
import onlinepractice.rv.quizzz.repository.PackRepository;
import onlinepractice.rv.quizzz.repository.QuizRepository;
import onlinepractice.rv.quizzz.repository.UserRepository;

@Service("InstaMojoPaymentService")
@Transactional
public class InstaMojoPaymentServiceImpl implements InstaMojoPaymentService {
	
	ApiContext context = ApiContext.create(CLIENT_ID, CLIENT_SECRET, ApiContext.Mode.LIVE);
	Instamojo api = new InstamojoImpl(context);
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private PackRepository packRepository;
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(InstaMojoPaymentServiceImpl.class);
	
	private PaymentOrder convertUserOrderToPaymentOrder(UserOrder uodr){
		
	PaymentOrder order = new PaymentOrder();
	order.setName(uodr.getUser().getUsername());
	order.setEmail(uodr.getUser().getEmail());
	order.setPhone(uodr.getMobileno());
	order.setCurrency(Currency);
	
	initializePaymentAmount(uodr, order);
	
	order.setDescription("enrollment request for quiz id "+uodr.getQuizId()+" from user mobile no - "+uodr.getMobileno());
	order.setRedirectUrl(REDIRECT_URL);
	//order.setWebhookUrl(Webhookurl);
	//order.setTransactionId(String.valueOf(uodr.hashCode()));
	order.setTransactionId(UUID.randomUUID().toString());
	
	
	return order;
	
	}

	/**
	 * @param uodr
	 * @param order
	 */
	private void initializePaymentAmount(UserOrder uodr, PaymentOrder order) {
		if (uodr.getIsBulkOrder() == false) {
			order.setAmount(quizRepository.findOne(uodr.getQuizId()).getQuizPrize());	
		}
		else {
			order.setAmount(packRepository.findOne(uodr.getPackId()).getPackPrize());
		}
	}

	@Override
	public PaymentOptions createPaymentOrder(UserOrder order)
			throws ResourceUnavailableException {
		
		// persiste user Oder
		UserOrder persistedOrder = userOrderService.saveUserOrder(order);
		
		// convert to payment order
		PaymentOrder paymentorder = convertUserOrderToPaymentOrder(persistedOrder);
		
		 PaymentOrderResponse paymentOrderResponse = null;
		try {
		    paymentOrderResponse = api.createPaymentOrder(paymentorder);
		    Long pgOrderId = Long.getLong(paymentOrderResponse.getPaymentOrder().getId());
		    String pgTxnId = paymentOrderResponse.getPaymentOrder().getTransactionId();
		    String pgOrderStatus = paymentOrderResponse.getPaymentOrder().getStatus();
		    
		    UserPayment userPayment = new UserPayment();
		    userPayment.setPgOrderId(pgOrderId);
		    userPayment.setPgTxnId(pgTxnId);
		    userPayment.setPgOrderStatus(pgOrderStatus);
		    userPayment.setUserOrder(persistedOrder);
		    
		    userPaymentService.saveUserPayment(userPayment);
		    
		   logger.debug(paymentOrderResponse.getPaymentOrder().getStatus());
		    

		} catch (HTTPException e) {
		   logger.error("erro code :: ", e.getStatusCode());
		   logger.error(e.getMessage());
		   logger.error(e.getJsonPayload());

		} catch (ConnectionException e) {
		   logger.error(e.getMessage());
		}
		
		return paymentOrderResponse.getPaymentOptions();
	}

	@Override
	public String getPaymentOrderStatus(UserOrder uodr) throws ResourceUnavailableException, ConnectionException, HTTPException {
		
		// load from db
		UserOrder loadedOrder = userOrderService.findByQuiznUser(uodr.getQuizId(), uodr.getUser());
		UserPayment upayment = userPaymentService.findByUserOrder(loadedOrder);
		String oldStatus = upayment.getPgOrderStatus();
		
		if (COMPLETE.equalsIgnoreCase(oldStatus)){
	    	subscribeforQuiznPack(loadedOrder);
	    	return oldStatus;
	    }
		
		// get payment gateway to get update
		PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(upayment.getPgTxnId());
	   logger.debug(paymentOrder.getId());
	   logger.debug(paymentOrder.getStatus());
		
	    String newStatus = paymentOrder.getStatus();
	    
	    if (!newStatus.equalsIgnoreCase(oldStatus)){
	    	upayment.setPgOrderStatus(newStatus);
	    	userPaymentService.saveUserPayment(upayment);
	    }
	    
	    if (COMPLETE.equalsIgnoreCase(newStatus)){
	    	// subcribe user to quiz
	    	subscribeforQuiznPack(loadedOrder);
	    }
		return paymentOrder.getStatus();
	}

	/**
	 * @param query
	 * @param user
	 */
	private void subscribeforQuiznPack(UserOrder uodr) {
		User user = uodr.getUser();
		Quiz quiz = quizRepository.findOne(uodr.getQuizId());
		Pack pack = packRepository.findOne(uodr.getPackId());
		
		List<Quiz> enrolledQuizs = user.getEnrolledquizes();
		
		if (uodr.getIsBulkOrder() == false) { // process subcription for single quiz
			processforQuiz(user, quiz, enrolledQuizs);
		}else { // process subscription for multiple quiz 
			List<Quiz> packQuizes = pack.getPackQuizs();
			for(Quiz q : packQuizes) {
				processforQuiz(user, q, enrolledQuizs);
				enrolledQuizs = user.getEnrolledquizes();
			}
		}
		
		
		
		
	}

	/**
	 * @param user
	 * @param quiz
	 * @param enrolledQuizs
	 */
	private void processforQuiz(User user, Quiz quiz, List<Quiz> enrolledQuizs) {
		// if not already enrolled 
		if (enrolledQuizs.isEmpty() || !enrolledQuizs.contains(quiz)){
			user.enrollQuiz(quiz);
			userRepository.save(user);
			//quizRepository.save(quiz);
		}
	}

	@Override
	public UserPayment update(
			PaymentGatewayResponse paymentGatewayResponse) {
		
		UserPayment userpayment = userPaymentService.findByTxnId(paymentGatewayResponse.getPgtxnId());
		UserOrder userOrder = userpayment.getUserOrder();
		// reload order from db 
		UserOrder loadedOrder = userOrderService.find(userOrder.getId());
		
		String oldStatus = userpayment.getPgOrderStatus();
	    String newStatus = paymentGatewayResponse.getPgPaymetStatus();
	    
	    if (!newStatus.equalsIgnoreCase(oldStatus)){
	    	userpayment.setPgOrderStatus(newStatus);
	    	userpayment.setPgId(paymentGatewayResponse.getPgId());
	    	userpayment.setPgPaymentId(paymentGatewayResponse.getPgPaymentId());
	    	
	    	userpayment.setUserOrder(loadedOrder);
	    	userPaymentService.saveUserPayment(userpayment);
	    }
	    
	    if (COMPLETE.equalsIgnoreCase(newStatus)){
	    	// subcribe user to quiz
	    	subscribeforQuiznPack(loadedOrder);
	    }
		return userpayment;
	}

	
	
	
	

}
