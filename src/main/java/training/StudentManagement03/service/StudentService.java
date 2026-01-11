package training.StudentManagement03.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.StudentManagement03.controller.converter.StudentConverter;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.domain.StudentSearchCondition;
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
   * 検索条件を指定して受講生詳細を検索します。
   *
   * @param condition 検索条件
   * @return 受講生詳細一覧
   */
  public List<StudentDetail> searchStudentsByCondition(StudentSearchCondition condition) {
    List<Student> studentList = repository.searchStudentsByCondition(condition);
    List<StudentCourse> studentCourseList = repository.searchCourse();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }


  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> courses = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, courses);
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生・受講生コース情報・受講状況を個別に登録します。
   * 受講生コース情報には受講生ID・コース開始日・コース終了日を設定します。
   * 受講状況は初期状態として「仮申し込み」を登録します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourses(studentCourse, student.getId());
      repository.insertStudentCourse(studentCourse);

      CourseStatus status = new CourseStatus();
      status.setStudentCourseId(studentCourse.getId());
      status.setStatus("仮申し込み");
      repository.insertCourseStatus(status);
    });
    return searchStudent(student.getId());
  }

  /**
   *受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourse 受講生コース情報
   * @param id 受講生ID
   */
  void initStudentsCourses(StudentCourse studentCourse, int id) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(id);
    studentCourse.setCourseStart(Timestamp.valueOf(now));
    studentCourse.setCourseEnd(Timestamp.valueOf(now.plusYears(1)));
  }

  /**
   * 受講生詳細の更新を行います。 受講生・受講生コース情報・受講状況をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   * @return　更新後の受講生詳細
   */
  @Transactional
  public StudentDetail updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList().forEach(course -> {
      repository.updateStudentCourse(course);
      CourseStatus status = course.getCourseStatus();
      repository.updateCourseStatus(status);
    });
    return studentDetail;
  }
}
