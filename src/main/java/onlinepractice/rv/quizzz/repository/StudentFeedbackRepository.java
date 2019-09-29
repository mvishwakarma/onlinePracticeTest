package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Answer;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.StudentFeedback;

@Repository("StudentFeedbackRepository")
public interface StudentFeedbackRepository extends JpaRepository<StudentFeedback, Long> {

	int countById(Long id);
	
	//int countByQuestion(List<Question> questions);

	//List<Answer> findByQuestionOrderByOrderAsc(Question question);
	

}
