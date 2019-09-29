package onlinepractice.rv.quizzz.service;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UserAlreadyExistsException;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;

public interface UserOrderService  {
	UserOrder saveUserOrder(UserOrder userOrder) throws UserAlreadyExistsException;

	UserOrder find(Long id) throws ResourceUnavailableException;;

	UserOrder findByEmail(String email) throws ResourceUnavailableException;
	
	UserOrder findByQuiznUser(Long quiz_id,User user) throws ResourceUnavailableException;

}