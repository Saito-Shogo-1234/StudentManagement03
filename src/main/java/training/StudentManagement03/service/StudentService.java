package training.StudentManagement03.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;
import training.StudentManagement03.domain.StudentDetail;
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

  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);
    return studentDetail;
  }

  public List<StudentCourses> searchStudentsCourseList() {
    return repository.searchCourse();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    for(StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      studentCourses.setStudentId(studentDetail.getStudent().getId());
      studentCourses.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
      studentCourses.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
      repository.insertStudentCourses(studentCourses);
    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentCourses course : studentDetail.getStudentCourses()) {
      repository.updateStudentCourses(course);
    }
  }
}
