package training.StudentManagement03.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import training.StudentManagement03.data.CourseStatus;
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
  void 受講生と受講生コースと受講状況を渡して受講生詳細のリストを返すこと() {
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("A");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("B");

    List<Student> studentList = List.of(student1, student2);

    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(2);

    CourseStatus status1 = new CourseStatus();
    status1.setStudentCourseId(1);
    status1.setStatus("仮申し込み");

    CourseStatus status2 = new CourseStatus();
    status2.setStudentCourseId(2);
    status2.setStatus("本申し込み");

    CourseStatus status3 = new CourseStatus();
    status3.setStudentCourseId(3);
    status3.setStatus("本申し込み");

    course1.setCourseStatus(status1);
    course2.setCourseStatus(status2);
    course3.setCourseStatus(status3);

    List<StudentCourse> courseList = List.of(course1, course2, course3);

    List<StudentDetail> studentDetails =
        sut.convertStudentDetails(studentList, courseList);

    assertThat(studentDetails.get(0).getStudentCourseList().get(0).getCourseStatus())
        .isEqualTo(status1);

    assertThat(studentDetails.get(0).getStudentCourseList().get(1).getCourseStatus())
        .isEqualTo(status2);

    assertThat(studentDetails.get(1).getStudentCourseList().get(0).getCourseStatus())
        .isEqualTo(status3);
  }

  @Test
  void 受講生と受講生コース情報が紐づいていない場合は除外すること() {
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("A");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("B");

    List<Student> studentList = List.of(student1, student2);

    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(3);

    CourseStatus status1 = new CourseStatus();
    status1.setStudentCourseId(1);
    status1.setStatus("仮申し込み");

    CourseStatus status2 = new CourseStatus();
    status2.setStudentCourseId(2);
    status2.setStatus("本申し込み");

    CourseStatus status3 = new CourseStatus();
    status3.setStudentCourseId(3);
    status3.setStatus("本申し込み");

    course1.setCourseStatus(status1);
    course2.setCourseStatus(status2);
    course3.setCourseStatus(status3);

    List<StudentCourse> courseList = List.of(course1, course2, course3);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(studentList, courseList);

    assertThat(studentDetails.getFirst().getStudentCourseList().get(0))
        .isEqualTo(courseList.get(0));
    assertThat(studentDetails.getFirst().getStudentCourseList().get(1))
        .isEqualTo(courseList.get(1));

    assertThat(studentDetails.get(0).getStudentCourseList().get(0).getCourseStatus())
        .isEqualTo(status1);
    assertThat(studentDetails.get(0).getStudentCourseList().get(1).getCourseStatus())
        .isEqualTo(status2);

    assertThat(studentDetails.get(1).getStudent()).isEqualTo(studentList.get(1));
    assertThat(studentDetails.get(1).getStudentCourseList()).isEmpty();

  }
}