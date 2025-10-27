package training.StudentManagement03.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import training.StudentManagement03.controller.converter.StudentConverter;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentsList")
  public List<StudentDetail> getStudentsList() {
    List<Student> students = service.searchStudentsList();
    List<StudentCourses> studentCourses = service.searchStudentsCourseList();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/studentsCourseList")
  public List<StudentCourses> getStudentsCourseList() {
    return service.searchStudentsCourseList();
  }

  @GetMapping("/30studentsList")
  public List<StudentDetail> get30StudentsList() {
    List<Student> students = service.search30StudentsList();
    List<StudentCourses> studentCourses = service.search30StudentsCourseList();

    return converter.convertStudentDetails(students, studentCourses);
  }

  @GetMapping("/30studentCourseList")
  public List<StudentCourses> get30StudentsCourseList() {
    return service.search30StudentsCourseList();
  }
}
