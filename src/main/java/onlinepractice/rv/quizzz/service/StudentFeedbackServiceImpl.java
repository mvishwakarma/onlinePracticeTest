package onlinepractice.rv.quizzz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.StudentFeedback;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.repository.StudentFeedbackRepository;

@Service("StudentFeedbackService")
@Transactional
public class StudentFeedbackServiceImpl implements StudenFeedbackService {

	private static final Logger logger = LoggerFactory.getLogger(StudentFeedbackServiceImpl.class);
	
	
	private StudentFeedbackRepository studentFeedbackRepository;


	@Autowired
	public StudentFeedbackServiceImpl(StudentFeedbackRepository sfr){
		this.studentFeedbackRepository = sfr;
	}
	
	@Override
	public StudentFeedback save(StudentFeedback studentFeedbacs , User user)
			throws UnauthorizedActionException {
		
		studentFeedbacs.setCreatedBy(user);
		
		logger.debug(" trying to save student feedback for ");
		return studentFeedbackRepository.save(studentFeedbacs);
		
	}

	@Override
	public StudentFeedback find(Long id) throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentFeedback update(StudentFeedback newstudentFeedbac)
			throws UnauthorizedActionException, ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(StudentFeedback studentFeedbacs)
			throws UnauthorizedActionException, ResourceUnavailableException {
		// TODO Auto-generated method stub

	}

}
