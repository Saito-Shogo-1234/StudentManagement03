package training.StudentManagement03.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourses> studentCourses;
}
