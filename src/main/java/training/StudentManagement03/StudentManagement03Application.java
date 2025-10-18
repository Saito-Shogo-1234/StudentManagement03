package training.StudentManagement03;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement03Application {

	private String name = "Aiu Eo";
	private String age = "10";
	private Map<String,String> student = new LinkedHashMap<>();

	{
		student.put("睦月","1月");
		student.put("如月","2月");
		student.put("弥生","3月");
		student.put("卯月","4月");
		student.put("皐月","5月");
		student.put("水無月","6月");
		student.put("文月","7月");
		student.put("葉月","8月");
		student.put("長月","9月");
		student.put("神無月","10月");
		student.put("霜月","11月");
		student.put("師走","12月");
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement03Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo() {
		return name + " " + age + "歳";
	}

	@GetMapping("/getMonth")
	public Map<String, String> getMonth() {
		return student;
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(String name, String age) {
		this.name = name;
		this.age = age;
	}

	@PostMapping("/studentName")
	public void setStudentName(String name) {
		this.name = name;
	}

	@PostMapping("/setMonth")
	public void setMonth(String oldMonth, String newMonth) {
		student.put(oldMonth, newMonth);
	}
}

// localhost:8080
// HTTPメソッドの　GETとPOSt
// GETは取得、リクエストの結果を受け取る。
// POSTは情報を与える、渡す。