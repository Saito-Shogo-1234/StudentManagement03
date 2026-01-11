package training.StudentManagement03.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.domain.StudentSearchCondition;
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
  void searchStudentsByCondition_条件なしの場合_全受講生詳細を取得できる() {
    List<Student> students = List.of(new Student());
    List<StudentCourse> courses = List.of(new StudentCourse());
    List<StudentDetail> details = List.of(new StudentDetail());

    when(repository.searchStudentsByCondition(null)).thenReturn(students);
    when(repository.searchCourse()).thenReturn(courses);
    when(converter.convertStudentDetails(students, courses)).thenReturn(details);

    List<StudentDetail> result = sut.searchStudentsByCondition(null);

    verify(repository).searchStudentsByCondition(null);
    verify(repository).searchCourse();
    verify(converter).convertStudentDetails(students, courses);

    assertEquals(details, result);
  }

  @Test
  void searchStudentsByCondition_条件指定の場合_該当する受講生詳細のみ取得できる() {
    StudentSearchCondition condition = new StudentSearchCondition();

    List<Student> students = List.of(new Student());
    List<StudentCourse> courses = List.of(new StudentCourse());
    List<StudentDetail> details = List.of(new StudentDetail());

    when(repository.searchStudentsByCondition(condition)).thenReturn(students);
    when(repository.searchCourse()).thenReturn(courses);
    when(converter.convertStudentDetails(students, courses)).thenReturn(details);

    List<StudentDetail> result = sut.searchStudentsByCondition(condition);

    assertEquals(details, result);
  }

  @Test
  void searchStudent_ID指定の場合_受講生詳細を取得できる() {
    int id = 1;

    Student student = new Student();
    student.setId(id);

    StudentCourse course = new StudentCourse();
    List<StudentCourse> courses = List.of(course);

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(id)).thenReturn(courses);

    StudentDetail result = sut.searchStudent(id);

    verify(repository).searchStudent(id);
    verify(repository).searchStudentCourse(id);

    assertEquals(student, result.getStudent());
    assertEquals(courses, result.getStudentCourseList());
  }

  @Test
  void registerStudent_受講生とコースと受講状況が登録される() {
    Student student = new Student();
    student.setId(1);

    StudentCourse course = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(course));

    when(repository.searchStudent(1)).thenReturn(student);
    when(repository.searchStudentCourse(1)).thenReturn(List.of(course));

    sut.registerStudent(detail);

    verify(repository).insertStudent(student);
    verify(repository).insertStudentCourse(course);
    verify(repository).insertCourseStatus(any(CourseStatus.class));
  }

  @Test
  void registerStudent_コース開始日終了日とstudentIdが設定される() {
    Student student = new Student();
    student.setId(1);

    StudentCourse course = new StudentCourse();
    StudentDetail detail = new StudentDetail(student, List.of(course));

    when(repository.searchStudent(1)).thenReturn(student);
    when(repository.searchStudentCourse(1)).thenReturn(List.of(course));

    sut.registerStudent(detail);

    assertEquals(1, course.getStudentId());
    assertNotNull(course.getCourseStart());
    assertNotNull(course.getCourseEnd());
  }

  @Test
  void updateStudent_受講生とコースと受講状況が更新される() {
    Student student = new Student();

    CourseStatus status = new CourseStatus();
    StudentCourse course = new StudentCourse();
    course.setCourseStatus(status);

    StudentDetail detail = new StudentDetail(student, List.of(course));

    sut.updateStudent(detail);

    verify(repository).updateStudent(student);
    verify(repository).updateStudentCourse(course);
    verify(repository).updateCourseStatus(status);
  }
}
