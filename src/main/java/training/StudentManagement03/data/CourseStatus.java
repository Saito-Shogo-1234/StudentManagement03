package training.StudentManagement03.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講状況")
@Getter
@Setter
public class CourseStatus {

  private int id;
  private int courseId;
  private String status;
}
