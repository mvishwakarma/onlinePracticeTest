package onlinepractice.rv.quizzz.service.downloadmanager;

import java.io.InputStream;

public interface awss3Objects {
	
	InputStream  getS3Objects(String bucketname, String key);

}
