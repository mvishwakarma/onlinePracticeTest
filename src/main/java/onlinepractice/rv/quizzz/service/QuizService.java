package onlinepractice.rv.quizzz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.support.Response;
import onlinepractice.rv.quizzz.model.support.Result;

public interface QuizService {
	Quiz save(Quiz quiz, User user);

	Page<Quiz> findAll(Pageable pageable);

	Page<Quiz> findAllFreePublished(Pageable pageable);
	
	Page<Quiz> findAllPublished(Pageable pageable);
	
	Page<Quiz> findAllPublishedForUser(User user,Pageable pageable);
	
	Page<Quiz> findAllAvailableForUser(User user,Pageable pageable);

	Page<Quiz> findQuizzesByUser(User user, Pageable pageable);
	
	Page<Quiz> findQuizzesEnrolledByUser(User user, Pageable pageable);

	Quiz find(Long id) throws ResourceUnavailableException;

	Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Quiz> search(String query, Pageable pageable);

	Result checkAnswers(User loggedinUser,Quiz quiz, List<Response> answersBundle,boolean persist);
	
	void publishQuiz(Quiz quiz);
	
	Page<Quiz> filterByTag(Page<Quiz> quizes,String tags);
}
