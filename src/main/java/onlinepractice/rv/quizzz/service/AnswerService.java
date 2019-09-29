package onlinepractice.rv.quizzz.service;

import java.util.List;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.Answer;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;

public interface AnswerService {
	Answer save(Answer answer) throws UnauthorizedActionException;

	Answer find(Long id) throws ResourceUnavailableException;

	Answer update(Answer newAnswer) throws UnauthorizedActionException, ResourceUnavailableException;

	void delete(Answer answer) throws UnauthorizedActionException, ResourceUnavailableException;

	/*List<Answer> findAnswersByQuestion(Question question);

	int countAnswersInQuestion(Question question);*/
	
	List<Answer> findAnswersByQuiz(Quiz quiz);
	
	int countAnswersInQuiz(Quiz quiz);
}
