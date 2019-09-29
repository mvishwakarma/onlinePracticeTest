package onlinepractice.rv.quizzz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onlinepractice.rv.quizzz.model.Pack;
import onlinepractice.rv.quizzz.model.PackDescreption;
import onlinepractice.rv.quizzz.repository.EBookRepository;
import onlinepractice.rv.quizzz.repository.PackRepository;

@Service("PackService")
@Transactional
public class PackServiceImpl implements PackService {
	
	private static final Logger logger = LoggerFactory.getLogger(PackServiceImpl.class);
	

	@Autowired
	private PackRepository packRepository;


	@Override
	public List<Pack> findAll() {
		// TODO Auto-generated method stub
		
		return packRepository.findAll();
	}


	@Override
	public List<Pack> findAllPublished() {
		// TODO Auto-generated method stub
		return packRepository.findAllIsPublishedTrue();
	}


	@Override
	public List<Pack> findAllPublishedByTags(String tags) {
		// TODO Auto-generated method stub
		return packRepository.findByTags(tags);
	}


	@Override
	public PackDescreption getPackPaymentDescreption(Long pack_id) {
		// TODO Auto-generated method stub
		
		PackDescreption pdescreption = new PackDescreption();
		pdescreption.setCourseDescreption("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah ");
		pdescreption.setCourseObjective("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah ");
		return pdescreption;
	}
	
	


}
