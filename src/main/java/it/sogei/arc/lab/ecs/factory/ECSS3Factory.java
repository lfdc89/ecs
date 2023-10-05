package it.sogei.arc.lab.ecs.factory;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.emc.object.s3.S3Client;
import com.emc.object.s3.S3Config;
import com.emc.object.s3.jersey.S3JerseyClient;

@Service
public class ECSS3Factory {

	@Value("${ecs.username}")
	public String S3_ACCESS_KEY_ID;
 
	@Value("${ecs.password}")
    public String S3_SECRET_KEY;

	@Value("${ecs.endpoint}")
    public String S3_URI;

	@Value("${ecs.bucket}")
    public String S3_BUCKET;

	@Value("${ecs.namespace}")
    public String S3_ECS_NAMESPACE;	

    private S3Client s3client;

    public synchronized S3Client getS3Client() throws URISyntaxException {

        if (s3client == null) {
            S3Config config = new S3Config(new URI(S3_URI));
            config.withIdentity(S3_ACCESS_KEY_ID).withSecretKey(S3_SECRET_KEY);
            s3client = new S3JerseyClient(config);
        }
        return s3client;
    }

}