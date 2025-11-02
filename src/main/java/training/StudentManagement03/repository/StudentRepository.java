package training.StudentManagement03.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchCourse();

  @Insert("INSERT INTO students(name,kana_name,nick_name,email,area,age,gender,remark,is_deleted) "
      + "VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id,course_name,course_start,course_end) "
      + "VALUES(#{studentId}, #{courseName}, #{courseStart}, #{courseEnd})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudentCourses(StudentCourses studentCourses);
}
