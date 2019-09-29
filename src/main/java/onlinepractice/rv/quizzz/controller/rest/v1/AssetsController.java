package onlinepractice.rv.quizzz.controller.rest.v1;



import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import onlinepractice.rv.quizzz.exceptions.UnauthorizedActionException;
import onlinepractice.rv.quizzz.model.AuthenticatedUser;
import onlinepractice.rv.quizzz.model.User;
import onlinepractice.rv.quizzz.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang.RandomStringUtils;

@RestController
@RequestMapping(AssetsController.ROOT_MAPPING)
public class AssetsController {
	
	public static final String ROOT_MAPPING = "/api/assets";
	 private static final Logger logger = LoggerFactory.getLogger(AssetsController.class);

	    @Autowired
	    private FileStorageService fileStorageService;
	

	 @RequestMapping(value = "/downloadFile/{fileName:.+}", method = RequestMethod.GET)
	 @PreAuthorize("isAuthenticated()")
	 public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, @AuthenticationPrincipal AuthenticatedUser user,HttpServletRequest request) {
		 
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
		 	// Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            logger.info("Could not determine file type.");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }
	        
	        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(8), "pdf");
	        
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
	                .body(resource);
	    }
}
