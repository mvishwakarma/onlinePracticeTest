package onlinepractice.rv.quizzz.service;

import java.util.List;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.model.Menu;

public interface MenuService {
	
	Menu find(Long id) throws ResourceUnavailableException;
	
	List<Menu> findAll() throws ResourceUnavailableException;

	List<Menu> findAllIsPublishedTrue() throws ResourceUnavailableException;
	
	
}
