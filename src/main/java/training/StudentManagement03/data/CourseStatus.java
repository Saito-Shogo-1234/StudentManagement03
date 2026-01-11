package training.StudentManagement03.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講状況")
@Getter
@Setter
public class CourseStatus {

  @Schema(description = "受講状況ID", example = "1")
  private int id;

  @Schema(description = "受講生コースID（StudentCourse.id）", example = "10")
  private int studentCourseId;

  @Schema(description = "受講ステータス", example = "受講中", allowableValues = {"仮申し込み", "本申し込み", "受講中", "受講終了"})
  @Pattern(
      regexp = "仮申し込み|本申し込み|受講中|受講終了",
      message = "ステータスは「仮申し込み」「本申し込み」「受講中」「受講終了」のいずれかを指定してください。")
  private String status;
}
