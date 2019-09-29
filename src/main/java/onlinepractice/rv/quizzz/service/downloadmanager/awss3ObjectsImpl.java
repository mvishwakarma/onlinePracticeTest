package onlinepractice.rv.quizzz.service.downloadmanager;

import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class awss3ObjectsImpl implements awss3Objects {

	 
	 
	  
	@Override
	public InputStream getS3Objects(String bucketName,String key) {
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

	    try {
	      System.out.println("Downloading an object");
	      S3Object s3object = s3Client.getObject(
	          new GetObjectRequest(bucketName, key));
	      return(s3object.getObjectContent());
	    }
	    catch(AmazonServiceException ase) {
	      System.err.println("Exception was thrown by the service");
	    }
	    catch(AmazonClientException ace) {
	      System.err.println("Exception was thrown by the client");
	    }
		return null;
	  

	}
}
