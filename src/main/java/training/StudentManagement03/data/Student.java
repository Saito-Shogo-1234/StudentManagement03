package training.StudentManagement03.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  private int id;

  @NotBlank
  @Size(max = 20, message = "名前は20文字以内で入力してください")
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}

