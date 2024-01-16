package it.sogei.arc.lab.ecs.controller;

import com.emc.object.s3.bean.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emc.object.s3.bean.ListObjectsResult;
import com.google.gson.Gson;

import it.sogei.arc.lab.ecs.service.EcsObjectOperationService;

@RestController
@RequestMapping("/ecs/object")
public class EcsObjectController {

	private static final Logger logger = LoggerFactory.getLogger(EcsObjectController.class);

	@Autowired
	EcsObjectOperationService service;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			logger.info("File da caricare:");
			logger.info("Nome: {}", file.getOriginalFilename());
			logger.info("Content-Type: {}", file.getContentType());
			logger.info("Size: {}", file.getSize());

			PutObjectResult pores = this.service.putFileToStorage(file);
			
			return ResponseEntity.ok().body(new Gson().toJson(pores));
		}
		catch(Exception e) {
			logger.error("{} - {}",e.getClass().getSimpleName(), e.getLocalizedMessage());
 			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<String> listBucketFiles() {
		try {
			
			ListObjectsResult res = this.service.listBucketFiles();
			
			return ResponseEntity.ok().body(new Gson().toJson(res));
		} catch(Exception e) {
			logger.error("{} - {}",e.getClass().getSimpleName(), e.getLocalizedMessage());
 			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}
	
	@PostMapping("/uploadaws")
	public ResponseEntity<String> uploadFileOnAWS(@RequestParam("file") MultipartFile file) {
		try {
			logger.info("File da caricare:");
			logger.info("Nome: {}", file.getOriginalFilename());
			logger.info("Content-Type: {}", file.getContentType());
			logger.info("Size: {}", file.getSize());

			this.service.putFileToStorageAWS(file);
			
			return ResponseEntity.ok().body(new Gson().toJson("Upload complete"));
		}
		catch(Exception e) {
			logger.error("{} - {}",e.getClass().getSimpleName(), e.getLocalizedMessage());
 			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}

}
