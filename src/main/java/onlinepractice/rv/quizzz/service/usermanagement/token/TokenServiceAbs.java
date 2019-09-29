package onlinepractice.rv.quizzz.service.usermanagement.token;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import onlinepractice.rv.quizzz.exceptions.InvalidTokenException;
import onlinepractice.rv.quizzz.model.TokenModel;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.repository.TokenRepository;
import onlinepractice.rv.quizzz.service.usermanagement.utils.DateHelper;
import onlinepractice.rv.quizzz.service.usermanagement.utils.TokenGenerator;

@Transactional
public abstract class TokenServiceAbs<T extends TokenModel> implements TokenService<T> {

	private TokenRepository<T> tokenRepo;
	private TokenGenerator tokenGenerator;

	@Autowired
	private DateHelper dateHelper;

	@Autowired
	public TokenServiceAbs(TokenGenerator tokenGenerator, TokenRepository<T> tokenRepo) {
		this.tokenRepo = tokenRepo;
		this.tokenGenerator = tokenGenerator;
	}

	public void setDateHelper(DateHelper dateHelper) {
		this.dateHelper = dateHelper;
	}

	@Override
	public T generateTokenForUser(User user) {
		T tokenModel = create();
		String token = tokenGenerator.generateRandomToken();

		tokenModel.setToken(token);
		tokenModel.setUser(user);
		tokenModel.setExpirationDate(
				dateHelper.getExpirationDate(dateHelper.getCurrentDate(), getExpirationTimeInMinutes()));

		return save(tokenModel);
	}

	@Override
	public void validateTokenForUser(User user, String token) {
		T tokenModel = findByToken(token);

		if (tokenModel == null) {
			throw new InvalidTokenException("Can't find token " + token);
		}

		if (!tokenModel.getUser().equals(user)) {
			throw new InvalidTokenException("Token " + token + " doesn't belong to user " + user.getId());
		}
	}

	@Override
	public void invalidateToken(String token) {
		T tokenModel = findByToken(token);
		delete(tokenModel);
	}

	@Override
	public void invalidateExpiredTokensPreviousTo(Date date) {
		tokenRepo.deletePreviousTo(date);
	}

	/*
	 * Override to modify default token persistence behavior.
	 */
	protected T save(T tokenModel) {
		return tokenRepo.save(tokenModel);
	}

	/*
	 * Override to modify default token deletion behavior.
	 */
	protected void delete(T tokenModel) {
		tokenRepo.delete(tokenModel);
	}

	/*
	 * Override to modify default token look-up behavior.
	 */
	protected T findByToken(String token) {
		return tokenRepo.findByToken(token);
	}

	protected abstract T create();

	protected abstract int getExpirationTimeInMinutes();

}
