package onlinepractice.rv.quizzz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.model.Menu;
import onlinepractice.rv.quizzz.repository.MenuRepository;

@Service("MenuService")
@Transactional
public class MenuServiceImpl implements MenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuRepository menuRepository;
	
	

	@Override
	public Menu find(Long id) throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> findAll() throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return menuRepository.findAll();
		//return null;
	}

	@Override
	public List<Menu> findAllIsPublishedTrue() throws ResourceUnavailableException {
		// TODO Auto-generated method stub
		return menuRepository.findAllIsPublishedTrue();
	}

}
