package onlinepractice.rv.quizzz.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UserAlreadyExistsException;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;
import onlinepractice.rv.quizzz.repository.UserPaymentRepository;

@Service
@Transactional
public class UserPaymentServiceImpl implements UserPaymentService {
	
	@Autowired
	UserPaymentRepository userPaymentRepository;



	@Override
	public UserPayment saveUserPayment(UserPayment userPayment) throws UserAlreadyExistsException {
		return userPaymentRepository.save(userPayment);
	}

	@Override
	public UserPayment find(Long id) throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPayment findByTxnId(String txnId) throws ResourceUnavailableException {
		return userPaymentRepository.findByPgTxnId(txnId);
	}

	@Override
	public UserPayment findByUserOrder(UserOrder userOrder)
			throws ResourceUnavailableException {
		return userPaymentRepository.findByUserOrder(userOrder);
	}

}
