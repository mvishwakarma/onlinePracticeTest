package onlinepractice.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Query;

@Repository("ContactQueryRepository")
public interface ContactQueryRepository extends JpaRepository<Query, Long> {

	//List<Query> findByEmail();
	
	//List<Query> findByPhoneNo();
	
	//List<Query> findByCreatedDate();
	
	//List<Query> findByQueryfrom();

}
