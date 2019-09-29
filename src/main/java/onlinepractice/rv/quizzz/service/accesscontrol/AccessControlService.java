package onlinepractice.rv.quizzz.service.accesscontrol;

import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.BaseModel;
import onlinepractice.rv.quizzz.model.UserOwned;

public interface AccessControlService<T extends BaseModel> {
	void canUserCreateObject(AuthenticatedUser user, T object) throws UnauthorizedActionException;

	void canCurrentUserCreateObject(T object) throws UnauthorizedActionException;

	void canUserReadObject(AuthenticatedUser user, Long id) throws UnauthorizedActionException;

	void canCurrentUserReadObject(Long id) throws UnauthorizedActionException;

	void canUserReadAllObjects(AuthenticatedUser user) throws UnauthorizedActionException;

	void canCurrentUserReadAllObjects() throws UnauthorizedActionException;

	void canUserUpdateObject(AuthenticatedUser user, T object) throws UnauthorizedActionException;

	void canCurrentUserUpdateObject(T object) throws UnauthorizedActionException;

	void canUserDeleteObject(AuthenticatedUser user, T object) throws UnauthorizedActionException;

	void canCurrentUserDeleteObject(T object) throws UnauthorizedActionException;
	
	void canCurrentUserPlayQuiz(T object) throws UnauthorizedActionException;
}
