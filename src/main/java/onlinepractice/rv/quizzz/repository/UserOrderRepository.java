package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOrder;


@Repository("UserOrderRepository")
public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {
	List<UserOrder> findByCreatedBy(User User);

	List<UserOrder> findByMobileno(String mobileno);
	
	List<UserOrder> findByQuizId(String quizId);

	@Query("select o from UserOrder o where o.quizId = :quiz_id and o.createdBy = :user ORDER BY o.createdDate DESC ")
	List<UserOrder> findByQuiznUser(Long quiz_id, User User);
}
