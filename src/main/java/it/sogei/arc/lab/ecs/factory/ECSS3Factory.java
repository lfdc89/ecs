package it.sogei.arc.lab.ecs.factory;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.emc.object.s3.S3Client;
import com.emc.object.s3.S3Config;
import com.emc.object.s3.jersey.S3JerseyClient;

@Service
public class ECSS3Factory {

	// ECS connection params
	@Value("${ecs.username}")
	private String ECS_ACCESS_KEY_ID;
 
	@Value("${ecs.password}")
	private String ECS_SECRET_KEY;

	@Value("${ecs.endpoint}")
	private String ECS_URI;

	@Value("${ecs.namespace}")
	private String ECS_NAMESPACE;	

	
	// AWS Bucket connection params
	@Value("${aws.username}")
	private String AWS_USERNAME;
	
	@Value("${aws.password}")
	private String AWS_PASSWORD;
	
	@Value("${aws.endpoint}")
	private String AWS_ENDPOINT;
	
	@Value("${aws.region}")
	private String AWS_REGION;
	
	
	// Proxy settings
	@Value("${proxy.host}")
	private String PROXY_HOST;
	
	@Value("${proxy.port}")
	private String PROXY_PORT;
	
	
	// Proxy labels
	private final String LABEL_HTTP_PROXY_HOST = "http.proxyHost";
	private final String LABEL_HTTP_PROXY_PORT = "http.proxyPort";
	private final String LABEL_HTTPS_PROXY_HOST = "https.proxyHost";
	private final String LABEL_HTTPS_PROXY_PORT = "https.proxyPort";
	
    private S3Client s3client;

    public synchronized S3Client getS3Client() throws Exception {

        if (s3client == null) {
            S3Config config = new S3Config(new URI(ECS_URI));
            config.withIdentity(ECS_ACCESS_KEY_ID).withSecretKey(ECS_SECRET_KEY).withNamespace(ECS_NAMESPACE);
            s3client = new S3JerseyClient(config);
            return s3client;
        } else {
    		throw new Exception("Client gia istanziato");
    	}
        
    }
    
    private void setProxy() {
		  System.setProperty(this.LABEL_HTTP_PROXY_HOST, this.PROXY_HOST);
	      System.setProperty(this.LABEL_HTTP_PROXY_PORT, this.PROXY_PORT);
	      System.setProperty(this.LABEL_HTTPS_PROXY_HOST, this.PROXY_HOST);
	      System.setProperty(this.LABEL_HTTPS_PROXY_PORT, this.PROXY_PORT);
	}
    
    public S3Client getS3AWSClient() throws Exception {

    	this.setProxy();
    	if(s3client == null) {
    		S3Config config = new S3Config(new URI(AWS_ENDPOINT));
    		config.withIdentity(AWS_USERNAME).withSecretKey(AWS_PASSWORD);
    		config.setPort(80);
    		S3Client s3client = new S3JerseyClient(config);
    		return s3client;
    	} else {
    		throw new Exception("Client gia istanziato");
    	}
    	
    }

}