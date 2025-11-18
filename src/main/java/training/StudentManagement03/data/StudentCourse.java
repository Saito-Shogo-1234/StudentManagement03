package training.StudentManagement03.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;
  private String courseName;
  private Timestamp courseStart;
  private Timestamp courseEnd;
}
