package onlinepractice.rv.quizzz.service;



import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.StudentFeedback;
import onlinepractice.rv.quizzz.model.User;


public interface StudenFeedbackService {
	StudentFeedback save(StudentFeedback studentFeedbacs , User user) throws UnauthorizedActionException;

	StudentFeedback find(Long id) throws ResourceUnavailableException;

	StudentFeedback update(StudentFeedback newstudentFeedbac) throws UnauthorizedActionException, ResourceUnavailableException;

	void delete(StudentFeedback studentFeedbacs) throws UnauthorizedActionException, ResourceUnavailableException;
}
