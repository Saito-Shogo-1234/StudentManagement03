package training.StudentManagement03.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生と受講生コース情報を組み合わせて受講生詳細を返すこと() {
    Student student = new Student();
    student.setId(1);
    student.setName("テスト");

    List<Student> studentList = List.of(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);
    studentCourse.setCourseName("テストコース");

    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(studentDetails.get(0).getStudent()).isEqualTo(student);
    assertThat(studentDetails.get(0).getStudentsCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生と受講生コース情報が紐づいていない場合は除外すること() {
    Student student = new Student();
    student.setId(1);
    student.setName("テスト");

    List<Student> studentList = List.of(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(2);
    studentCourse.setCourseName("テストコース");

    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(studentDetails.get(0).getStudent()).isEqualTo(student);
    assertThat(studentDetails.get(0).getStudentsCourseList()).isEmpty();
  }
}