package onlinepractice.rv.quizzz.service;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UserAlreadyExistsException;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;

public interface UserPaymentService  {
	UserPayment saveUserPayment(UserPayment userPayment) throws UserAlreadyExistsException;

	UserPayment find(Long id) throws ResourceUnavailableException;;

	UserPayment  findByTxnId(String txnId) throws ResourceUnavailableException;
	
	UserPayment findByUserOrder(UserOrder userOrder) throws ResourceUnavailableException;


}