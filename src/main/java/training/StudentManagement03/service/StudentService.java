package training.StudentManagement03.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;
import training.StudentManagement03.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentsList() {
    return repository.search();
  }

  public List<StudentCourses> searchStudentsCourseList() {
    return repository.searchCourse();
  }

  public List<Student> search30StudentsList() {
    return repository.search().stream()
        .filter(s -> s.getAge() >= 15 && s.getAge() < 17)
        .collect(Collectors.toList());
  }

  public List<StudentCourses> search30StudentsCourseList() {
    return repository.searchCourse().stream()
        .filter(s -> s.getCourseName().equals("サッカー")||s.getCourseName().equals("野球"))
        .collect(Collectors.toList());
  }
}
