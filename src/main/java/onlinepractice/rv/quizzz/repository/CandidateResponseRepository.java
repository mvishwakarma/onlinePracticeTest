package onlinepractice.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.CandidateResponse;
import onlinepractice.rv.quizzz.model.User;

@Repository("candidateResponseRepository")
public interface CandidateResponseRepository extends JpaRepository<CandidateResponse, Long> {
	User findByQuizId(Long quidId);

	User findByCreatedById(Long CreatedById);
}
