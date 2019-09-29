package onlinepractice.rv.quizzz.service.usermanagement;

import onlinepractice.rv.quizzz.model.User;

public interface UserManagementService {

	void resendPassword(User user);

	void verifyResetPasswordToken(User user, String token);

	void updatePassword(User user, String password);

}
