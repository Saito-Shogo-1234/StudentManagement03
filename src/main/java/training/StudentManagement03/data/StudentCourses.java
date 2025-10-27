package training.StudentManagement03.data;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

  private int id;
  private int studentId;
  private String courseName;
  private Timestamp courseStart;
  private Timestamp courseEnd;
}
