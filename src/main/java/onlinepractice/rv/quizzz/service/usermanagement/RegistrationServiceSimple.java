package onlinepractice.rv.quizzz.service.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;

import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.UserService;

public class RegistrationServiceSimple implements RegistrationService {

	@Autowired
	private UserService userService;

	@Override
	public User startRegistration(User user) {
		User newUser = userService.saveUser(user);
		userService.setRegistrationCompleted(newUser);

		return newUser;
	}

	@Override
	public User continueRegistration(User user, String token) {
		return null;
	}

	@Override
	public boolean isRegistrationCompleted(User user) {
		return userService.isRegistrationCompleted(user);
	}

}
