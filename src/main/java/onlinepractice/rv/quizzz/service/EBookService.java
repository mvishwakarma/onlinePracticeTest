package onlinepractice.rv.quizzz.service;

import java.util.List;

import onlinepractice.rv.quizzz.exceptions.ResourceUnavailableException;
import onlinepractice.rv.quizzz.model.EBook;

public interface EBookService {

	public List<EBook> findActiveEBooks() ;
	
	public  List<EBook> findActiveEBooksByStream(String stream) ;
}
