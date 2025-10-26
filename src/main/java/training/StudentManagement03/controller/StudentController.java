package training.StudentManagement03.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentsList")
  public List<Student> getStudentsList() {
    return service.searchStudentsList();
  }

  @GetMapping("/studentsCourseList")
  public List<StudentCourse> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/30studentsList")
  public List<Student> get30StudentsList() {
    return service.search30StudentsList();
  }

  @GetMapping("/30studentCourseList")
  public List<StudentCourse> get30StudentsCourseList() {
    return service.search30StudentsCourseList();
  }
}
