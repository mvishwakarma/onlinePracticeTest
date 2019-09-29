package onlinepractice.rv.quizzz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UserAlreadyExistsException;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.repository.UserOrderRepository;


@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {
	
	@Autowired
	UserOrderRepository userOrderRepository;

	

	@Override
	public UserOrder saveUserOrder(UserOrder userOrder) throws UserAlreadyExistsException {
		
		return userOrderRepository.save(userOrder);
	}

	@Override
	public UserOrder find(Long id) throws ResourceUnavailableException {
		return userOrderRepository.findOne(id);
	}

	@Override
	public UserOrder findByEmail(String email) throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserOrder findByQuiznUser(Long quiz_id, User user)
			throws ResourceUnavailableException {
		List<UserOrder> listofOrders = userOrderRepository.findByQuiznUser(quiz_id,user);
		System.out.println("count for list of order found are "+listofOrders.size()+" for quiz_id "+ quiz_id + " and user "+user.getId());
		return listofOrders.get(0);
	}

}
