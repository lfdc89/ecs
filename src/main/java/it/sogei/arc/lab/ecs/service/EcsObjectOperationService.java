package it.sogei.arc.lab.ecs.service;

import com.emc.object.s3.bean.PutObjectResult;
import com.emc.object.s3.request.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emc.object.s3.S3Client;
import com.emc.object.s3.bean.ListObjectsResult;

import it.sogei.arc.lab.ecs.factory.ECSS3Factory;

@Service
public class EcsObjectOperationService {

	private static final Logger logger = LoggerFactory.getLogger(EcsObjectOperationService.class);
	
	@Value("${ecs.bucket}")
    public String ECS_BUCKET;
	
	@Value("${aws.bucket}")
	private String AWS_BUCKET;
	
	@Autowired
	ECSS3Factory factory;
	
	public PutObjectResult putFileToStorage(MultipartFile file) throws Exception {
		try {
			S3Client s3 = factory.getS3Client();
			PutObjectRequest poreq = new PutObjectRequest(ECS_BUCKET, null, file.getBytes());
			//PutObjectResult por = s3.putObject(ECS_BUCKET, file.getOriginalFilename(), file.getBytes(), file.getContentType());
			PutObjectResult por = s3.putObject(poreq);

			logger.info(String.format("created object [%s/%s] with content: [%s]", ECS_BUCKET, file.getOriginalFilename(), file.getBytes()));

			return por;
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}
	
	
	public ListObjectsResult listBucketFiles() throws Exception {
		try {
			S3Client s3 = factory.getS3Client();
			ListObjectsResult listResult = s3.listObjects(ECS_BUCKET);
			return listResult;
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}
	
	public void putFileToStorageAWS(MultipartFile file) throws Exception {
		try {
			S3Client s3 = factory.getS3AWSClient();
			s3.putObject(AWS_BUCKET, file.getOriginalFilename(), file.getBytes(), file.getContentType());

			logger.info(String.format("created object [%s/%s] with content: [%s]", AWS_BUCKET, file.getOriginalFilename(), file.getBytes()));
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}
	
}
