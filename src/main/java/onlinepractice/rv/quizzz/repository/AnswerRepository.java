package onlinepractice.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import onlinepractice.rv.quizzz.model.Answer;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;

@Repository("AnswerRepository")
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	int countByQuiz(Quiz quiz);
	
	//int countByQuestion(List<Question> questions);

	//List<Answer> findByQuestionOrderByOrderAsc(Question question);
	
	List<Answer> findByQuizOrderByOrderAsc(Quiz quiz);

}
