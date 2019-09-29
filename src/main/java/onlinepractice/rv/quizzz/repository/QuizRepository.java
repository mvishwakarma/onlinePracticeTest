package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;

@Repository("QuizRepository")
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

	Page<Quiz> findByIsPublishedTrue(Pageable pageable);
	
	//Page<Quiz> findByIsPublishedTrue();
	
	@Query("select q from Quiz q where q.isFree = true and q.isPublished = true ")
	Page<Quiz> findByIsPublishedTrueANDIsFreeTrue(Pageable pageable);
	
	@Query("select q from Quiz q where q.isFree = false and q.isPublished = true ")
	Page<Quiz> findByIsPublishedTrueANDIsFreeFalse(Pageable pageable);

	Page<Quiz> findByCreatedBy(User user, Pageable pageable);
	
	@Query("select q from Quiz q where q.isFree = true and q.isPublished = true and user in (q.enrolledUsers) ")
	Page<Quiz> findEligibleForUsers(User user, Pageable pageable);
	
	@Query("select q from Quiz q where q.isFree = false and q.isPublished = true and user not in (q.enrolledUsers) ")
	Page<Quiz> findNONEligibleForUsers(User user, Pageable pageable);
	
	Page<Quiz> findByEnrolledUsers(List<User> enrolldUsers, Pageable pageable);

	@Query("select q from Quiz q where q.name like %?1%")
	Page<Quiz> searchByName(String name, Pageable pageable);
}
