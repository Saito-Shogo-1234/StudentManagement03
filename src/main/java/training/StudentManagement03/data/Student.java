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

  @Schema(description = "受講生ID", example = "1")
  private int id;

  @Schema(description = "氏名", example = "円堂守")
  @NotBlank(message = "名前は必須です")
  @Size(max = 20, message = "名前は20文字以内で入力してください")
  private String name;

  @Schema(description = "カナ氏名", example = "エンドウマモル")
  private String kanaName;

  @Schema(description = "ニックネーム", example = "エンドウ")
  private String nickname;

  @Schema(description = "メールアドレス", example = "endo@example.com")
  private String email;

  @Schema(description = "居住地域", example = "東京")
  private String area;

  @Schema(description = "年齢", example = "15")
  private int age;

  @Schema(description = "性別", example = "男性")
  private String gender;

  @Schema(description = "備考")
  private String remark;

  @Schema(description = "論理削除フラグ（true: 削除済み）", example = "false")
  private boolean isDeleted;
}
