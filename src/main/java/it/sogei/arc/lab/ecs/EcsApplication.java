package it.sogei.arc.lab.ecs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcsApplication.class, args);
	}

}
