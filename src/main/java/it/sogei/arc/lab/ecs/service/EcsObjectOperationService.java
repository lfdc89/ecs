package it.sogei.arc.lab.ecs.service;

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
    public String S3_BUCKET;
	
	@Autowired
	ECSS3Factory factory;
	
	public void putFileToStorage(MultipartFile file) {
		try {
			S3Client s3 = factory.getS3Client();
			s3.putObject(S3_BUCKET, file.getName(), file.getBytes(), file.getContentType());

			logger.info(String.format("created object [%s/%s] with content: [%s]", S3_BUCKET, file.getName(), file.getBytes()));
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	
	public ListObjectsResult listBucketFiles() throws Exception {
		try {
			S3Client s3 = factory.getS3Client();
			ListObjectsResult listResult = s3.listObjects(S3_BUCKET);
			return listResult;
		} catch(Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}
	
}
