package training.StudentManagement03.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchCourse();

  @Insert("INSERT INTO students(name,kana_name,nick_name,email,area,age,gender,remark) VALUES(#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{gender}, #{remark})")
  void insertStudent(Student student);
}
