package training.StudentManagement03.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  @Schema(description = "受講生コースID", example = "1")
  private int id;

  @Schema(description = "受講生ID（Student.id）", example = "1")
  private int studentId;

  @Schema(description = "コース名", example = "サッカー")
  private String courseName;

  @Schema(description = "コース開始日時", example = "2025-04-01T00:00:00.000+00:00")
  private Timestamp courseStart;

  @Schema(description = "コース終了日時", example = "2025-07-01T00:00:00.000+00:00")
  private Timestamp courseEnd;

  @Schema(description = "コース受講ステータス")
  private CourseStatus courseStatus;
}
