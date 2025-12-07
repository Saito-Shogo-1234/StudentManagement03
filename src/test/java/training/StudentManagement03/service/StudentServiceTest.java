package training.StudentManagement03.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import training.StudentManagement03.controller.converter.StudentConverter;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentCourseDetail;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターの処理が適切に呼びだせていること() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<CourseStatus> courseStatusList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchCourse()).thenReturn(studentCourseList);
    when(repository.searchStatus()).thenReturn(courseStatusList);

    sut.searchStudentsList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchCourse();
    verify(repository, times(1)).searchStatus();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList, courseStatusList);
  }

  @Test
  void IDに紐づく受講生詳細の検索＿リポジトリの処理が適切に呼びだせていること() {
    int id = 777;

    Student student = new Student();
    student.setId(id);

    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId(100);
    studentCourse1.setStudentId(student.getId());

    StudentCourse studentCourse2 = new StudentCourse();
    studentCourse2.setId(200);
    studentCourse2.setStudentId(student.getId());

    List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2);

    CourseStatus status1 = new CourseStatus();
    status1.setId(10);
    status1.setStudentCourseId(studentCourse1.getId());

    CourseStatus status2 = new CourseStatus();
    status2.setId(20);
    status2.setStudentCourseId(studentCourse2.getId());

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);
    when(repository.searchCourseStatus(studentCourse1.getId())).thenReturn(status1);
    when(repository.searchCourseStatus(studentCourse2.getId())).thenReturn(status2);

    StudentDetail result = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(student.getId());
    verify(repository, times(1)).searchStudentCourse(student.getId());
    verify(repository, times(1)).searchCourseStatus(studentCourse1.getId());
    verify(repository, times(1)).searchCourseStatus(studentCourse2.getId());

    assertEquals(student, result.getStudent());
    assertEquals(studentCourseList.get(0), result.getStudentCourseDetailList().get(0).getStudentCourse());
    assertEquals(studentCourseList.get(1), result.getStudentCourseDetailList().get(1).getStudentCourse());
    assertEquals(status1, result.getStudentCourseDetailList().get(0).getCourseStatus());
    assertEquals(status2, result.getStudentCourseDetailList().get(1).getCourseStatus());
  }

  @Test
  void 受講生詳細の登録＿リポジトリの処理が適切に呼びだせていること() {
    Student student = new Student();

    StudentCourse studentCourse = new StudentCourse();
    CourseStatus courseStatus = new CourseStatus();

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, courseStatus);
    List<StudentCourseDetail> studentCourseDetailList = List.of(studentCourseDetail);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseDetailList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).insertStudent(student);
    verify(repository, times(1)).insertStudentCourse(studentCourse);
    verify(repository, times(1)).insertCourseStatus(courseStatus);
  }

  @Test
  void 受講生詳細の登録＿初期化処理が行われていること() {
    int id = 777;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse = new StudentCourse();

    LocalDateTime now = LocalDateTime.now();
    sut.initStudentsCourses(studentCourse, student.getId());

    assertEquals(777, studentCourse.getStudentId());
    assertEquals(Timestamp.valueOf(now), studentCourse.getCourseStart());
    assertEquals(Timestamp.valueOf(now.plusYears(1)), studentCourse.getCourseEnd());
  }

  @Test
  void 受講生詳細の更新＿リポジトリの処理が適切に呼びだせていること() {
    Student student = new Student();

    StudentCourse studentCourse = new StudentCourse();
    CourseStatus courseStatus = new CourseStatus();

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, courseStatus);
    List<StudentCourseDetail> studentCourseDetailList = List.of(studentCourseDetail);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseDetailList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
    verify(repository, times(1)).updateCourseStatus(courseStatus);
  }
}