package com.repiso.sasiain.pablo.instaFake;

import com.repiso.sasiain.pablo.instaFake.shared.service.file.FileConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(FileConfig.class)
@SpringBootApplication
public class InstaFakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstaFakeApplication.class, args);
	}

}
