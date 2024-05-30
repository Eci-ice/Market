package com.example.maoliang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MaoliangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaoliangApplication.class, args);
	}

	@Configuration
	public class WebConfig implements WebMvcConfigurer {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/**")
					.addResourceLocations("classpath:/static/")
					.setCachePeriod(0);
		}
	}

	@RestController
	public class ApiController {
		@GetMapping("/api/data")
		@CrossOrigin(origins = "http://localhost:3000/") // Adjust origin as needed
		public String getData() {
			// Your API logic here
			return "Data from Spring Boot API";
		}
	}

}
