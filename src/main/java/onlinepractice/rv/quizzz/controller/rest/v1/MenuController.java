package onlinepractice.rv.quizzz.controller.rest.v1;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.Menu;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.MenuService;

@RestController
@RequestMapping(MenuController.ROOT_MAPPING)
public class MenuController {
	
	public static final String ROOT_MAPPING = "/api/menu";
	 private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	    @Autowired
	    private MenuService menuService;
	

	 @RequestMapping(value = "/practiceTest", method = RequestMethod.GET)
	 @PreAuthorize("isAuthenticated()")
	 public List<Menu> getMenu( @AuthenticationPrincipal AuthenticatedUser user,HttpServletRequest request) {
		 
		 	// check if you have valid user 
		 	if (user == null) {
		 		logger.info("user is null returning un auth exceprion");
		 		throw new UnauthorizedActionException();
		 	}
		 	if (user != null){
		 	User loggedInuser = user.getUser();
		 	if (loggedInuser == null){
		 		logger.info("logged in user is null returning un auth exceprion");
		 			throw new UnauthorizedActionException();
		 		}
		 	}
		 	
		 	return menuService.findAllIsPublishedTrue();
	 }

}
