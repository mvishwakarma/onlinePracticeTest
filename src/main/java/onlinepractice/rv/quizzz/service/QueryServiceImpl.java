package onlinepractice.rv.quizzz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.Query;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.repository.ContactQueryRepository;

@Service("QueryService")
@Transactional
public class QueryServiceImpl implements QueryService {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);
	
	@Autowired
	private ContactQueryRepository queryRepository ;

	
	@Autowired
	public QueryServiceImpl(ContactQueryRepository queryRepository) {
		this.queryRepository = queryRepository;
	}

	@Override
	public Query save(Query query,User user) throws UnauthorizedActionException {
		query.setCreatedBy(user);
		Query q = queryRepository.save(query);
		return q;
	}

	@Override
	public Query find(Long id) throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

}
