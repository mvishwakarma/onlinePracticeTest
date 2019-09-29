package onlinepractice.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;
import onlinepractice.rv.quizzz.model.UserPayment;

@Repository("UserPaymentRepository")
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
	UserPayment findByPgOrderId(Long pgOrderId);

	UserPayment findByPgTxnId(String pgTxnId);
	
	UserPayment findByUserOrder(UserOrder userOrder);
}
