package training.StudentManagement03.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentSearchCondition;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * IDに紐づく受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(int id);

  /**
   * 検索条件を指定して論理削除されていない受講生を検索します。
   *
   * @param condition 検索条件
   * @return 受講生一覧
   */
  List<Student> searchStudentsByCondition(StudentSearchCondition condition);

  /**
   * 受講生コース情報を受講状況付きで全件検索を行います。
   *
   * @return 受講生のコース情報(受講状況付きで全件)
   */
  List<StudentCourse> searchCourse();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(int studentId);

  /**
   * 受講生を新規登録します。
   * IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void insertStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。
   * IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 受講状況を新規登録します。
   * IDに関しては自動採番を行う。
   *
   * @param courseStatus 受講状況
   */
  void insertCourseStatus(CourseStatus courseStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講状況を更新します。
   *
   * @param courseStatus 受講状況
   */
  void updateCourseStatus(CourseStatus courseStatus);
}
