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
  void 受講生と受講生コース情報を渡して受講生詳細のリストを返すこと() {
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("A");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("B");

    List<Student> students = List.of(student1, student2);

    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);
    course1.setCourseName("テスト1コース");

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);
    course2.setCourseName("テスト2コース");

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(2);
    course3.setCourseName("テスト3コース");

    List<StudentCourse> courses = List.of(course1, course2, course3);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(students, courses);

    assertThat(studentDetails.get(0).getStudent()).isEqualTo(students.get(0));
    assertThat(studentDetails.get(0).getStudentsCourseList())
        .containsExactly(courses.get(0), courses.get(1));

    assertThat(studentDetails.get(1).getStudent()).isEqualTo(students.get(1));
    assertThat(studentDetails.get(1).getStudentsCourseList())
        .containsExactly(courses.get(2));
  }

  @Test
  void 受講生と受講生コース情報が紐づいていない場合は除外すること() {
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("A");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("B");

    List<Student> students = List.of(student1, student2);

    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);
    course1.setCourseName("テスト1コース");

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);
    course2.setCourseName("テスト2コース");

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(3);
    course3.setCourseName("テスト3コース");

    List<StudentCourse> courses = List.of(course1, course2, course3);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(students, courses);

    assertThat(studentDetails.get(0).getStudent()).isEqualTo(students.get(0));
    assertThat(studentDetails.get(0).getStudentsCourseList())
        .containsExactly(courses.get(0), courses.get(1));

    assertThat(studentDetails.get(1).getStudent()).isEqualTo(students.get(1));
    assertThat(studentDetails.get(1).getStudentsCourseList())
        .isEmpty();
  }
}