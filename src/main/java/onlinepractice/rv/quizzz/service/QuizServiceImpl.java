package onlinepractice.rv.quizzz.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.exceptions.ActionRefusedException;
import onlinepractice.rv.quizzz.exceptions.InvalidParametersException;
import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.CandidateResponse;
import onlinepractice.rv.quizzz.model.Question;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.support.Response;
import onlinepractice.rv.quizzz.model.support.Result;
import onlinepractice.rv.quizzz.repository.CandidateResponseRepository;
import onlinepractice.rv.quizzz.repository.QuizRepository;

@Service("QuizService")
@Transactional
public class QuizServiceImpl implements QuizService {

	private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);
	private QuizRepository quizRepository;

	private QuestionService questionService;
	
	private CandidateResponseRepository candidateResponseRepository ;
	
	private AnswerService answerService;

	@Autowired
	public QuizServiceImpl(QuizRepository quizRepository, QuestionService questionService, CandidateResponseRepository candidateResponseRepository,AnswerService answerService) {
		this.quizRepository = quizRepository;
		this.questionService = questionService;
		this.candidateResponseRepository = candidateResponseRepository;
		this.answerService = answerService;
	}

	@Override
	public Quiz save(Quiz quiz, User user) {
		quiz.setCreatedBy(user);
		return quizRepository.save(quiz);
	}

	@Override
	public Page<Quiz> findAll(Pageable pageable) {
		return quizRepository.findAll(pageable);
	}
	
	@Override
	public Page<Quiz> findAllFreePublished(Pageable pageable){
		return quizRepository.findByIsPublishedTrueANDIsFreeTrue(pageable);
	}

	@Override
	public Page<Quiz> findAllPublished(Pageable pageable) {
		return quizRepository.findByIsPublishedTrue(pageable);
	}

	@Override
	public Quiz find(Long id) throws ResourceUnavailableException {
		Quiz quiz = quizRepository.findOne(id);

		if (quiz == null) {
			logger.error("Quiz " + id + " not found");
			throw new ResourceUnavailableException("Quiz " + id + " not found");
		}

		return quiz;
	}

	@Override
	public Quiz update(Quiz newQuiz) throws UnauthorizedActionException, ResourceUnavailableException {
		Quiz currentQuiz = find(newQuiz.getId());

		mergeQuizzes(currentQuiz, newQuiz);
		return quizRepository.save(currentQuiz);
	}

	@Override
	public void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException {
		quizRepository.delete(quiz);
	}

	private void mergeQuizzes(Quiz currentQuiz, Quiz newQuiz) {
		currentQuiz.setName(newQuiz.getName());
		currentQuiz.setDescription(newQuiz.getDescription());
	}

	@Override
	public Page<Quiz> search(String query, Pageable pageable) {
		return quizRepository.searchByName(query, pageable);
	}

	@Override
	public Page<Quiz> findQuizzesByUser(User user, Pageable pageable) {
		return quizRepository.findByCreatedBy(user, pageable);
	}

	@Override
	public Result checkAnswers(User loggedinUser,Quiz quiz, List<Response> answersBundle,boolean persist) {
		Result results = new Result();
		//results.set
		for (Question question : quiz.getQuestions()) {
			boolean isFound = false;

			if (!question.getIsValid()) {
				continue;
			}

			for (Response bundle : answersBundle) {
				if (bundle.getQuestion().equals(question.getId())) {
					isFound = true; 
					logger.info(" Started evaluvation for question id = "+question.getId() );
					// create candidateResponse
					CandidateResponse candidateResponse = new CandidateResponse();
					candidateResponse.setQuizId(quiz.getId());
					
					candidateResponse.setQuestionId(question.getId());
					
					if(bundle.getSelectedAnswers() != null) {
						String answersOrderIdCSV = "";
						String answerIdCSV = "";
						for (Long answer :  bundle.getSelectedAnswers()) {
							answerIdCSV += String.valueOf(answer) + ",";
							Long answer_id = Long.valueOf(answerService.find(answer).getOrder());
							answersOrderIdCSV += String.valueOf(answer_id) + ",";
						}
						// trim last common
						if(answersOrderIdCSV.length() > 0)
						answersOrderIdCSV = answersOrderIdCSV.substring(0,answersOrderIdCSV.lastIndexOf(","));
						candidateResponse.setCandidateResponseIds(answerIdCSV); 
						candidateResponse.setCandidateResponseOrderIds(answersOrderIdCSV);	
					}
					else
					candidateResponse.setCandidateResponseOrderIds("-1");
					
					//candidateResponse.setCandidateResponseStatus(bundle.getSelectedAnswerStatus());
					Boolean evaluvationResult = questionService.checkIsCorrectAnswers(question, bundle.getSelectedAnswers());
					logger.info("  evaluvation result for question id = "+question.getId() + " is "+evaluvationResult.toString());
					candidateResponse.setIsCorrect(evaluvationResult);
					
					populateCorrectAnswers(question, candidateResponse);
					
					
					candidateResponse.setCreatedById(loggedinUser.getId());

					// save candidate response database .
					if (persist){
						candidateResponseRepository.save(candidateResponse);
					}
					
					results.addAnswer(evaluvationResult);
					results.addResponseBundle(candidateResponse);
					break;
				}
			}

			if (!isFound) {
				throw new InvalidParametersException("No answer found for question: " + question.getText());
			}
		}
		
		return results;
	}

	/**
	 * @param question
	 * @param candidateResponse
	 */
	private void populateCorrectAnswers(Question question, CandidateResponse candidateResponse) {
		if (null != question.getCorrectAnswer()) {
			candidateResponse.setCorrectAnswerOrderId(new Long(question.getCorrectAnswer().getOrder()));
			candidateResponse.setCorrectAnswerOrderIds(String.valueOf((question.getCorrectAnswer().getOrder())));
		}
		String answersCSV = question.getMultipleCorrectAnswers();
		
		if ( null != answersCSV) { // in case of more then one answer correct
			String[] temp = answersCSV.split(",");
			String correctAnswerOrderCSV = "";
			for (String v : temp) {
				int order = answerService.find(Long.decode(v)).getOrder();
				correctAnswerOrderCSV += String.valueOf(order) + ",";
			}
			// trim last comman
			if (correctAnswerOrderCSV.length() > 0)
			correctAnswerOrderCSV = correctAnswerOrderCSV.substring(0,correctAnswerOrderCSV.lastIndexOf(","));
			
			candidateResponse.setCorrectAnswerOrderIds(correctAnswerOrderCSV);
		}
	}

	@Override
	public void publishQuiz(Quiz quiz) {
		int count = questionService.countValidQuestionsInQuiz(quiz);

		if (count > 0) {
			quiz.setIsPublished(true);
			quizRepository.save(quiz);
		} else {
			throw new ActionRefusedException("The quiz doesn't have any valid questions");
		}
	}

	@Override
	public Page<Quiz> findQuizzesEnrolledByUser(User user, Pageable pageable) {
		List<User> enrolldUsers = new ArrayList<User>();
		enrolldUsers.add(user);
		return quizRepository.findByEnrolledUsers(enrolldUsers, pageable);
	}

	// this will give all quiz which are free or enrolled by user.
	@Override
	public Page<Quiz> findAllPublishedForUser(User user, Pageable pageable) {
		Page<Quiz> allPublishedQuiz = quizRepository.findByIsPublishedTrue( pageable);
		List<Quiz> filterredQuiz = new ArrayList<Quiz>(); 
		
		for (Quiz quiz : allPublishedQuiz) {
			if (null!= quiz){
			List<User> enroledUsrs = quiz.getEnrolledUsers();
			if (quiz.getIsFree() == true ){
				filterredQuiz.add(quiz);
			}else if (quiz.getIsFree() == false && null != enroledUsrs && !enroledUsrs.isEmpty() && enroledUsrs.contains(user)){
				filterredQuiz.add(quiz);
			}
			enroledUsrs = null;
			}
		}
		return new PageImpl<Quiz>(filterredQuiz);
	
	}
	
	public Page<Quiz> filterByTag(Page<Quiz> quizes,String tags){
		if(tags == null || "".equalsIgnoreCase(tags)) return quizes;
		List<Quiz> filterredQuiz = new ArrayList<Quiz>(); 
		for (Quiz quiz : quizes) {
			
			String qtags = quiz.getTags();
			if (qtags == null || "".equalsIgnoreCase(qtags)) continue;
			String[] qt = qtags.split(";");
			for (String q :qt ){
				if(tags.contains(q)){
					filterredQuiz.add(quiz);
					break;
				}
			}
		}
		return new PageImpl<Quiz>(filterredQuiz);
	}

	//this will give all quiz which are non free and non enrolled by user.
	@Override
	public Page<Quiz> findAllAvailableForUser(User user, Pageable pageable) {
	
		Page<Quiz> allQuiz = quizRepository.findByIsPublishedTrueANDIsFreeFalse( pageable);
		List<Quiz> filterredQuiz = new ArrayList<Quiz>(); 
		for (Quiz quiz : allQuiz) {
			if (null!= quiz){
			List<User> enroledUsrs = quiz.getEnrolledUsers();
			if (!enroledUsrs.contains(user)){
				filterredQuiz.add(quiz);
			}
			}
		}
		return new PageImpl<Quiz>(filterredQuiz);
	}

	

}
