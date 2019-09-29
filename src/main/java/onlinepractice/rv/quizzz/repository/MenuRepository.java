package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Menu;

@Repository("MenuRepository")
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	@Query("select m from Menu m where m.isPublished = true ")
	List<Menu> findAllIsPublishedTrue();


}
