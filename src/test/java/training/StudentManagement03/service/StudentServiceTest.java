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
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
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
    when(repository.search()).thenReturn(studentList);
    when(repository.searchCourse()).thenReturn(studentCourseList);

    sut.searchStudentsList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchCourse();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void IDに紐づく受講生詳細の検索＿リポジトリの処理が適切に呼びだせていること() {
    int id = 777;
    Student student = new Student();
    student.setId(id);
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    StudentDetail result = sut.searchStudent(id);

    verify(repository, times(1)).searchStudent(student.getId());
    verify(repository, times(1)).searchStudentCourse(student.getId());
    assertEquals(student, result.getStudent());
    assertEquals(studentCourseList, result.getStudentsCourseList());
  }

  @Test
  void 受講生詳細の登録＿リポジトリの処理が適切に呼びだせていること() {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.registerStudent(studentDetail);

    verify(repository, times(1)).insertStudent(student);
    verify(repository, times(1)).insertStudentCourse(studentCourse);
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
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }
}