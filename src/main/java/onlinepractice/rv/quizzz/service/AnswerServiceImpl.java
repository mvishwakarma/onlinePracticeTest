package onlinepractice.rv.quizzz.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.exceptions.ActionRefusedException;
import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.Answer;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.repository.AnswerRepository;

@Service("AnswerService")
@Transactional
public class AnswerServiceImpl implements AnswerService {

	private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);
	
	
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;

	@Autowired
	public AnswerServiceImpl(AnswerRepository answerRepository) {
		this.answerRepository = answerRepository;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
		
	}

	@Override
	public Answer find(Long id) throws ResourceUnavailableException {
		Answer answer = answerRepository.findOne(id);

		if (answer == null) {
			logger.error("Answer " + id + " not found");
			throw new ResourceUnavailableException("Answer " + id + " not found");
		}

		return answer;
	}

	@Override
	public Answer save(Answer answer) throws UnauthorizedActionException {
		return answerRepository.save(answer);
	}

	@Override
	public Answer update(Answer newAnswer) throws ResourceUnavailableException, UnauthorizedActionException {
		Answer currentAnswer = find(newAnswer.getId());

		mergeAnswers(currentAnswer, newAnswer);
		return answerRepository.save(currentAnswer);
	}

	@Override
	public void delete(Answer answer) throws ResourceUnavailableException, UnauthorizedActionException {

		/*if (questionService.checkIsCorrectAnswer(answer.getQuestion(), answer.getId())) {
			throw new ActionRefusedException("The correct answer can't be deleted");
		}*/

		answerRepository.delete(answer);
	}

	private void mergeAnswers(Answer currentAnswer, Answer newAnswer) {
		currentAnswer.setText(newAnswer.getText());

		if (newAnswer.getOrder() != null) {
			currentAnswer.setOrder(newAnswer.getOrder());
		}
	}
/*
	@Override
	public List<Answer> findAnswersByQuestion(Question question) {
		return answerRepository.findByQuestionOrderByOrderAsc(question);
	}

	@Override
	public int countAnswersInQuestion(Question question) {
		return answerRepository.countByQuestion(question);
	}*/

	@Override
	public List<Answer> findAnswersByQuiz(Quiz quiz) {
		
		return answerRepository.findByQuizOrderByOrderAsc(quiz);
	}

	@Override
	public int countAnswersInQuiz(Quiz quiz) {
		
		return answerRepository.countByQuiz(quiz);
	}
	

}
