package onlinepractice.rv.quizzz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.model.EBook;
import onlinepractice.rv.quizzz.repository.EBookRepository;

@Service("EBookService")
@Transactional
public class EBookServiceImpl implements EBookService {
	
	private static final Logger logger = LoggerFactory.getLogger(EBookServiceImpl.class);
	
	@Autowired
	private EBookRepository eBookRepository;

	@Override
	public List<EBook> findActiveEBooks() {
		// TODO Auto-generated method stub
		return eBookRepository.findAllIsPublishedTrue();
	}

	@Override
	public List<EBook> findActiveEBooksByStream(String stream) {
		// TODO Auto-generated method stub
		return eBookRepository.findByStream(stream);
	}



}
