package training.StudentManagement03.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.StudentManagement03.controller.converter.StudentConverter;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentCourseDetail;
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
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行わない。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentsList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchCourse();
    List<CourseStatus> statusList = repository.searchStatus();
    return converter.convertStudentDetails(studentList, studentCourseList, statusList);
  }

  /**
   * 受講生詳細検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース詳細を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourseList = repository.searchStudentCourse(student.getId());
    List<StudentCourseDetail> studentCourseDetailList = new ArrayList<>();
    for (StudentCourse studentCourse : studentCourseList) {
      CourseStatus status = repository.searchCourseStatus(studentCourse.getId());
      StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, status);
      studentCourseDetailList.add(studentCourseDetail);
    }
    return new StudentDetail(student, studentCourseDetailList);
  }

  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース詳細を個別に登録し、受講生コース情報には受講生詳細を紐づける値とコース開始日コース終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);

    studentDetail.getStudentCourseDetailList().forEach(studentCourseDetail -> {
      StudentCourse studentCourse = studentCourseDetail.getStudentCourse();
      initStudentsCourses(studentCourse, student.getId());
      repository.insertStudentCourse(studentCourse);

      CourseStatus status = studentCourseDetail.getCourseStatus();
      status.setStudentCourseId(studentCourse.getId());
      repository.insertCourseStatus(status);
    });
    return studentDetail;
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
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース詳細をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseDetailList().forEach(studentCourseDetail -> {
          StudentCourse studentCourse = studentCourseDetail.getStudentCourse();
          repository.updateStudentCourse(studentCourse);

          CourseStatus status = studentCourseDetail.getCourseStatus();
          repository.updateCourseStatus(status);
    });
  }
}
