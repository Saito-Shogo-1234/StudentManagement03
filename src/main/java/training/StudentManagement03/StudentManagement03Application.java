package training.StudentManagement03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement03Application {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement03Application.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
}

// localhost:8080