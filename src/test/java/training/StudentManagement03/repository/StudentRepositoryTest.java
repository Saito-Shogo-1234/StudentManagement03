package training.StudentManagement03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(14);
  }

  @Test
  void IDに紐づく受講生の検索が行えること() {
    Student actual = sut.searchStudent(1);
    assertThat(actual.getName()).isEqualTo("円堂守");
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchCourse();
    assertThat(actual.size()).isEqualTo(14);
  }

  @Test
  void IDに紐づく受講生コース情報の検索が行えること() {
    Student student = sut.searchStudent(1);
    List<StudentCourse> actual = sut.searchStudentCourse(student.getId());
    assertThat(actual.getFirst().getCourseName()).isEqualTo("サッカー");
  }

  @Test
  void 受講状況の全件検索が行えること() {
    List<StudentCourse> studentCourse = sut.searchStudentCourse(1);
    CourseStatus actual = sut.searchCourseStatus(studentCourse.getFirst().getId());
    assertThat(actual.getStatus()).isEqualTo("仮申し込み");
  }

  @Test
  void 受講生コース情報のIDに紐づく受講状況の検索が行えること() {
    List<CourseStatus> actual = sut.searchStatus();
    assertThat(actual.size()).isEqualTo(14);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(15);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseName("テストコース");
    studentCourse.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

    sut.insertStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchCourse();

    assertThat(actual.size()).isEqualTo(15);
  }

  @Test
  void 受講生状況の登録が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseName("テストコース");
    studentCourse.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

    sut.insertStudentCourse(studentCourse);

    CourseStatus courseStatus = new CourseStatus();
    courseStatus.setCourseId(studentCourse.getId());
    courseStatus.setStatus("本申し込み");

    sut.insertCourseStatus(courseStatus);

    List<CourseStatus> actual = sut.searchStatus();

    assertThat(actual.size()).isEqualTo(15);
    assertThat(actual.getLast().getStatus()).isEqualTo("本申し込み");
  }

  @Test
  void 受講生の更新が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    student.setAge(55);

    sut.updateStudent(student);

    Student actual = sut.searchStudent(student.getId());

    assertThat(actual.getAge()).isEqualTo(55);
  }

  @Test
  void 受講生コース情報の更新が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(15);
    studentCourse.setCourseName("テストコース");
    studentCourse.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

    sut.insertStudentCourse(studentCourse);

    studentCourse.setCourseName("テスト更新コース");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse(studentCourse.getStudentId());

    assertThat(actual.getFirst().getCourseName()).isEqualTo("テスト更新コース");
  }

  @Test
  void 受講状況の更新が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseName("テストコース");
    studentCourse.setCourseStart(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setCourseEnd(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

    sut.insertStudentCourse(studentCourse);

    CourseStatus courseStatus = new CourseStatus();
    courseStatus.setCourseId(studentCourse.getId());
    courseStatus.setStatus("仮申し込み");

    sut.insertCourseStatus(courseStatus);

    courseStatus.setStatus("本申し込み");

    sut.updateCourseStatus(courseStatus);

    CourseStatus actual = sut.searchCourseStatus(courseStatus.getCourseId());

    assertThat(actual.getStatus()).isEqualTo("本申し込み");
  }
}