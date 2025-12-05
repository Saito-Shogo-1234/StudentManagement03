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
  void 受講生と受講生コースと受講状況の情報を渡して受講生詳細のリストを返すこと() {
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
    course1.setCourseName("テスト1コース");

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);
    course2.setCourseName("テスト2コース");

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(2);
    course3.setCourseName("テスト3コース");

    List<StudentCourse> courseList = List.of(course1, course2, course3);

    CourseStatus courseStatus1 = new CourseStatus();
    courseStatus1.setId(1);
    courseStatus1.setCourseId(1);
    courseStatus1.setStatus("仮申し込み");

    CourseStatus courseStatus2 = new CourseStatus();
    courseStatus2.setId(2);
    courseStatus2.setCourseId(2);
    courseStatus2.setStatus("本申し込み");

    CourseStatus courseStatus3 = new CourseStatus();
    courseStatus3.setId(3);
    courseStatus3.setCourseId(3);
    courseStatus3.setStatus("本申し込み");

    List<CourseStatus> statusList = List.of(courseStatus1, courseStatus2, courseStatus3);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(studentList, courseList,
        statusList);

    assertThat(studentDetails.getFirst().getStudent()).isEqualTo(studentList.getFirst());
    assertThat(studentDetails.getFirst().getStudentsCourseList().get(0).getStudentCourse()).isEqualTo(courseList.get(0));
    assertThat(studentDetails.getFirst().getStudentsCourseList().get(1).getStudentCourse()).isEqualTo(courseList.get(1));
    assertThat(studentDetails.get(0).getStudentsCourseList().getFirst().getCourseStatus()).isEqualTo(courseStatus1);
    assertThat(studentDetails.get(0).getStudentsCourseList().get(1).getCourseStatus()).isEqualTo(courseStatus2);

    assertThat(studentDetails.get(1).getStudent()).isEqualTo(studentList.get(1));
    assertThat(studentDetails.get(1).getStudentsCourseList().get(0).getStudentCourse()).isEqualTo(courseList.get(2));
    assertThat(studentDetails.get(1).getStudentsCourseList().get(0).getCourseStatus()).isEqualTo(courseStatus3);
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
    course1.setCourseName("テスト1コース");

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);
    course2.setCourseName("テスト2コース");

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(3);
    course3.setCourseName("テスト3コース");

    List<StudentCourse> courseList = List.of(course1, course2, course3);

    CourseStatus courseStatus1 = new CourseStatus();
    courseStatus1.setId(1);
    courseStatus1.setCourseId(1);
    courseStatus1.setStatus("仮申し込み");

    CourseStatus courseStatus2 = new CourseStatus();
    courseStatus2.setId(2);
    courseStatus2.setCourseId(2);
    courseStatus2.setStatus("本申し込み");

    CourseStatus courseStatus3 = new CourseStatus();
    courseStatus3.setId(3);
    courseStatus3.setCourseId(3);
    courseStatus3.setStatus("本申し込み");

    List<CourseStatus> statusList = List.of(courseStatus1, courseStatus2, courseStatus3);

    List<StudentDetail> studentDetails = sut.convertStudentDetails(studentList, courseList, statusList);

    assertThat(studentDetails.getFirst().getStudent()).isEqualTo(studentList.getFirst());
    assertThat(studentDetails.getFirst().getStudentsCourseList().get(0).getStudentCourse()).isEqualTo(courseList.get(0));
    assertThat(studentDetails.getFirst().getStudentsCourseList().get(1).getStudentCourse()).isEqualTo(courseList.get(1));
    assertThat(studentDetails.get(0).getStudentsCourseList().getFirst().getCourseStatus()).isEqualTo(courseStatus1);
    assertThat(studentDetails.get(0).getStudentsCourseList().get(1).getCourseStatus()).isEqualTo(courseStatus2);

    assertThat(studentDetails.get(1).getStudent()).isEqualTo(studentList.get(1));
    assertThat(studentDetails.get(1).getStudentsCourseList())
        .isEmpty();
  }
}