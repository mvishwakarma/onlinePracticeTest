package onlinepractice.rv.quizzz.service.accesscontrol;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.BaseModel;
import onlinepractice.rv.quizzz.model.Quiz;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.model.UserOwned;

public abstract class AccessControlServiceUserOwned<T extends BaseModel & UserOwned>
		implements AccessControlService<T> {

	private static final Logger logger = LoggerFactory.getLogger(AccessControlServiceUserOwned.class);

	@Override
	public void canUserCreateObject(AuthenticatedUser user, T object) throws UnauthorizedActionException {
		if (!canUserModifyObject(user, object)) {
			logger.error("The user " + user.getId() + " can't create this object");
			throw new UnauthorizedActionException(
					"User " + user.getUsername() + " is not allowed to perform this action");
		}
	}

	@Override
	public void canCurrentUserCreateObject(T object) throws UnauthorizedActionException {
		canUserCreateObject(getCurrentUser(), object);
	}

	@Override
	public void canUserReadObject(AuthenticatedUser user, Long id) throws UnauthorizedActionException {
		// By default, anyone can read objects
		
	}

	@Override
	public void canCurrentUserReadObject(Long id) throws UnauthorizedActionException {
		canUserReadObject(getCurrentUser(), id);
	}

	@Override
	public void canUserReadAllObjects(AuthenticatedUser user) throws UnauthorizedActionException {
		// By default, anyone can read objects
	}

	@Override
	public void canCurrentUserReadAllObjects() throws UnauthorizedActionException {
		canUserReadAllObjects(getCurrentUser());
	}

	@Override
	public void canUserUpdateObject(AuthenticatedUser user, T object) throws UnauthorizedActionException {
		if (!canUserModifyObject(user, object)) {
			logger.error("The user " + ((user != null) ? user.getId() : "null") + " can't update this object");
			throw new UnauthorizedActionException("User " + ((user != null) ? user.getUsername() : "null")
					+ " is not allowed to perform this action");
		}
	}

	@Override
	public void canCurrentUserUpdateObject(T object) throws UnauthorizedActionException {
		canUserUpdateObject(getCurrentUser(), object);
	}

	@Override
	public void canUserDeleteObject(AuthenticatedUser user, T object) throws UnauthorizedActionException {
		if (!canUserModifyObject(user, object)) {
			logger.error("The user " + ((user != null) ? user.getId() : "null") + " can't delete this object");
			throw new UnauthorizedActionException("User " + ((user != null) ? user.getUsername() : "null")
					+ " is not allowed to perform this action");
		}
	}

	@Override
	public void canCurrentUserDeleteObject(T object) throws UnauthorizedActionException {
		canUserDeleteObject(getCurrentUser(), object);
	}

	private boolean canUserModifyObject(AuthenticatedUser user, UserOwned obj) {
		if (user == null) {
			return false;
		}

		return obj.getUser().equals(user.getUser());
	}
	
	@Override
	public void canCurrentUserPlayQuiz(T object) {
		
		Quiz quiz = (Quiz)object;
		User user = getCurrentUser().getUser();
		List<Quiz> enrolledQuiz = getCurrentUser().getUser().getEnrolledquizes();
		
		if (quiz!= null && quiz.getIsFree() == true ) return;
		if (enrolledQuiz != null && enrolledQuiz.contains(quiz)) return;
		logger.error("The user " + ((user != null) ? user.getId() : "null") + " can't Play this Quiz");
		throw new UnauthorizedActionException("User " + ((user != null) ? user.getUsername() : "null")
				+ " is not allowed to perform this action");
		
	}

	private AuthenticatedUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() == null || authentication.getPrincipal() instanceof String) {
			return null;
		}

		return (AuthenticatedUser) authentication.getPrincipal();
	}
}
