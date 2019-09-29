package onlinepractice.rv.quizzz.service;


import java.util.List;

import onlinepractice.rv.quizzz.model.Pack;
import onlinepractice.rv.quizzz.model.PackDescreption;

public interface PackService {
	
	public List<Pack> findAll();
	
	public List<Pack> findAllPublished();
	
	public List<Pack> findAllPublishedByTags(String tags);

	public PackDescreption getPackPaymentDescreption(Long pack_id);

}
