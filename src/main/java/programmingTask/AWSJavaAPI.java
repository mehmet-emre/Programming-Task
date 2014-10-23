package programmingTask;

import static programmingTask.Constants.AWS_BUCKET_NAME;
import static programmingTask.Constants.SC_FILE_NAME;

import java.io.File;
import java.io.IOException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AWSJavaAPI {

	private BasicAWSCredentials awsCreds;
	private AmazonS3 s3Client;
	
	public void checkCredentials(String accessKey, String secretAccessKey){
		awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);
		s3Client = new AmazonS3Client(awsCreds);
		s3Client.listBuckets();

	}
	
	public void uploadFile(String keyName) throws IOException {

        File file = new File(SC_FILE_NAME);
        s3Client.putObject(new PutObjectRequest(
        		AWS_BUCKET_NAME, keyName, file));

    }
}
