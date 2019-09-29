package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Answer;
import onlinepractice.rv.quizzz.model.EBook;
import onlinepractice.rv.quizzz.model.Pack;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;

@Repository("PackRepository")
public interface PackRepository extends JpaRepository<Pack, Long> {

	@Query("select p from Pack p where p.isPublished = true ")
	List<Pack> findAllIsPublishedTrue();
	
	@Query("select p from Pack p where p.isPublished = true and p.tags = ?1 ")
	List<Pack> findByTags(String tags);
	
		
	
}
