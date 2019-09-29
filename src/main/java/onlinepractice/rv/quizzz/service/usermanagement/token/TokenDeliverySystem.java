package onlinepractice.rv.quizzz.service.usermanagement.token;

import org.springframework.scheduling.annotation.Async;

import onlinepractice.rv.quizzz.model.TokenModel;
import onlinepractice.rv.quizzz.model.TokenType;
import onlinepractice.rv.quizzz.model.User;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}
