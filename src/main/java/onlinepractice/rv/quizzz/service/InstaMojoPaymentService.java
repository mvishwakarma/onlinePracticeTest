package onlinepractice.rv.quizzz.service;

import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOptions;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;
import onlinepractice.rv.quizzz.model.support.PaymentGatewayResponse;

public interface InstaMojoPaymentService {
	
	PaymentOptions createPaymentOrder(UserOrder userOrder) throws ResourceUnavailableException;
	
	String getPaymentOrderStatus(UserOrder uodr) throws ResourceUnavailableException, ConnectionException, HTTPException;
	
	UserPayment update(PaymentGatewayResponse paymentGatewayResponse);

}
