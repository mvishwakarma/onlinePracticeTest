package onlinepractice.rv.quizzz.service;

import java.util.List;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.User;

public interface QueryService {
	Query save(Query query,User user) throws UnauthorizedActionException;

	Query find(Long id) throws ResourceUnavailableException;


}
