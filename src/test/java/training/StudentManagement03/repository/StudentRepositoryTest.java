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
import training.StudentManagement03.domain.StudentSearchCondition;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void searchStudentsByCondition_条件なしの場合_全受講生を取得できる() {
    StudentSearchCondition condition = new StudentSearchCondition();
    List<Student> actual = sut.searchStudentsByCondition(condition);
    assertThat(actual).isNotEmpty();
  }

  @Test
  void searchStudent_ID指定の場合_該当する受講生を取得できる() {
    Student actual = sut.searchStudent(1);
    assertThat(actual.getName()).isEqualTo("円堂守");
  }

  @Test
  void searchCourse_全受講生コース情報を取得できる() {
    List<StudentCourse> actual = sut.searchCourse();
    assertThat(actual.size()).isEqualTo(14);
  }

  @Test
  void searchCourse_受講状況とJOINして取得できる() {
    List<StudentCourse> actual = sut.searchCourse();

    assertThat(actual).isNotEmpty();
    assertThat(actual.getFirst().getCourseStatus()).isNotNull();
    assertThat(actual.getFirst().getCourseStatus().getStatus())
        .isEqualTo("仮申し込み");
  }

  @Test
  void searchStudentCourse_ID指定の場合_該当するコースを取得できる() {
    Student student = sut.searchStudent(1);
    List<StudentCourse> actual = sut.searchStudentCourse(student.getId());
    assertThat(actual.getFirst().getCourseName()).isEqualTo("サッカー");
  }

  @Test
  void insertStudent_受講生を登録できる() {
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

    StudentSearchCondition condition = new StudentSearchCondition();
    List<Student> actual = sut.searchStudentsByCondition(condition);

    assertThat(actual)
        .extracting(Student::getName)
        .contains("試験");
  }

  @Test
  void insertStudentCourse_受講生コースを登録できる() {
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

    List<StudentCourse> actual = sut.searchStudentCourse(student.getId());

    assertThat(actual)
        .extracting(StudentCourse::getCourseName)
        .contains("テストコース");
  }

  @Test
  void insertCourseStatus_受講状況を登録できる() {
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

    CourseStatus status = new CourseStatus();
    status.setStudentCourseId(studentCourse.getId());
    status.setStatus("本申し込み");

    sut.insertCourseStatus(status);

    List<StudentCourse> actual = sut.searchCourse();

    assertThat(actual)
        .anySatisfy(course ->
            assertThat(course.getCourseStatus().getStatus())
                .isEqualTo("本申し込み"));
  }

  @Test
  void updateStudent_受講生情報を更新できる() {
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
  void updateStudentCourse_受講生コースを更新できる() {
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

    studentCourse.setCourseName("テスト更新コース");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse(studentCourse.getStudentId());

    assertThat(actual.getFirst().getCourseName()).isEqualTo("テスト更新コース");
  }

  @Test
  void updateCourseStatus_受講状況を更新できる() {
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
    courseStatus.setStudentCourseId(studentCourse.getId());
    courseStatus.setStatus("仮申し込み");

    sut.insertCourseStatus(courseStatus);

    courseStatus.setStatus("本申し込み");

    sut.updateCourseStatus(courseStatus);

    List<StudentCourse> actual = sut.searchCourse();

    assertThat(actual)
        .anySatisfy(course ->
            assertThat(course.getCourseStatus().getStatus())
                .isEqualTo("本申し込み"));
  }
}