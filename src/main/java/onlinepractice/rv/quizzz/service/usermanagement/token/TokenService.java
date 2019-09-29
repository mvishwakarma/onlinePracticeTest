package onlinepractice.rv.quizzz.service.usermanagement.token;

import java.util.Date;

import onlinepractice.rv.quizzz.exceptions.InvalidTokenException;
import onlinepractice.rv.quizzz.model.TokenModel;
import onlinepractice.rv.quizzz.model.User;

public interface TokenService<T extends TokenModel> {
	T generateTokenForUser(User user);

	void validateTokenForUser(User user, String token) throws InvalidTokenException;

	void invalidateToken(String token);

	void invalidateExpiredTokensPreviousTo(Date date);
}
