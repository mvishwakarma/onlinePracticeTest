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
import onlinepractice.rv.quizzz.model.Pack;
import onlinepractice.rv.quizzz.model.PackDescreption;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.PackService;

@RestController
@RequestMapping(PackController.ROOT_MAPPING)
public class PackController {
	
	public static final String ROOT_MAPPING = "/api/packs";
	private static final Logger logger = LoggerFactory.getLogger(PackController.class);

    @Autowired
    private PackService packService;
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
	 //@PreAuthorize("isAuthenticated()")
	 public List<Pack> getAllPacks( @AuthenticationPrincipal AuthenticatedUser user,HttpServletRequest request) {
    	
    	// check if you have valid user 
		/*
		 * if (user == null) { logger.info("user is null returning un auth exceprion");
		 * throw new UnauthorizedActionException(); } if (user != null){ User
		 * loggedInuser = user.getUser(); if (loggedInuser == null){
		 * logger.info("logged in user is null returning un auth exceprion"); throw new
		 * UnauthorizedActionException(); } }
		 */
	 	
    	return packService.findAllPublished(); 
    	
	 }
	 
	 @RequestMapping(value = "/{tags}/list", method = RequestMethod.GET)
	 public List<Pack> getAllPacksByStream( @AuthenticationPrincipal AuthenticatedUser user,@PathVariable String tags) {
		 	
		 return packService.findAllPublishedByTags(tags);
	 }
	 
	/*
	 * @RequestMapping(value = "/{pack_id}/list", method = RequestMethod.GET) public
	 * PackDescreption getAllPacksByStream( @AuthenticationPrincipal
	 * AuthenticatedUser user,@PathVariable Long pack_id) {
	 * 
	 * 
	 * return packService.getPackPaymentDescreption(pack_id); }
	 */
}
