package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.EBook;

@Repository("EBookRepository")
public interface EBookRepository extends JpaRepository<EBook, Long> {
	
	@Query("select e from EBook e where e.isPublished = true ")
	List<EBook> findAllIsPublishedTrue();
	
	@Query("select e from EBook e where e.isPublished = true and e.stream = ?1 ")
	List<EBook> findByStream(String stream);

}
