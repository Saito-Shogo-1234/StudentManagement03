package training.StudentManagement03.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.StudentManagement03.controller.converter.StudentConverter;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 検索や登録、更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生の一覧検索です。
   * 全件検索を行うので、条件指定は行わない。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentsList() {
    List<Student> studentList = repository.search();
    List<StudentCourses> studentCoursesList = repository.searchCourse();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
    return new StudentDetail(student, studentCourses);
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    for(StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      studentCourses.setStudentId(studentDetail.getStudent().getId());
      studentCourses.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
      studentCourses.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
      repository.insertStudentCourses(studentCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for(StudentCourses course : studentDetail.getStudentCourses()) {
      repository.updateStudentCourses(course);
    }
  }
}
