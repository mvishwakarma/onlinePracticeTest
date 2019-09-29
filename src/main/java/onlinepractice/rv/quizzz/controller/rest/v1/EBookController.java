package onlinepractice.rv.quizzz.controller.rest.v1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.EBook;
import onlinepractice.rv.quizzz.model.Menu;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.EBookService;

@RestController
@RequestMapping(EBookController.ROOT_MAPPING)
public class EBookController {
	
	public static final String ROOT_MAPPING = "/api/ebooks";
	 private static final Logger logger = LoggerFactory.getLogger(EBookController.class);
	 
	    @Autowired
	    private EBookService eBookService;
	    
		 @RequestMapping(value = "/all", method = RequestMethod.GET)
		 @PreAuthorize("isAuthenticated()")
		 public List<EBook> getAllBooks( @AuthenticationPrincipal AuthenticatedUser user,HttpServletRequest request) {
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
			 return eBookService.findActiveEBooks();
		 }
		 
		 @RequestMapping(value = "/{stream}", method = RequestMethod.GET)
		 @PreAuthorize("isAuthenticated()")
		 public List<EBook> getAllBooksByStream( @AuthenticationPrincipal AuthenticatedUser user,@PathVariable String stream) {
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
			 	
			 return eBookService.findActiveEBooksByStream(stream);
		 }



}
