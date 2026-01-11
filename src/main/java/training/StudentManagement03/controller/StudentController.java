package training.StudentManagement03.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.domain.StudentSearchCondition;
import training.StudentManagement03.service.StudentService;

/**
 * 受講生の検索や登録、更新を行うREST APIとして受け付るControllerです。
 */
@RestController
@Validated
public class StudentController {

  private StudentService service;


  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 検索条件を指定して受講生を検索します。
   * 条件なしで未削除の受講生を全件取得します。
   *
   * @param condition 検索条件
   * @return 受講生詳細一覧
   */
  @Operation(
      summary = "受講生一覧・条件検索",
      description = "条件を指定して受講生を検索します。条件なしで未削除の受講生を全件取得します。")
  @GetMapping("/students")
  public List<StudentDetail> getStudents(@ParameterObject StudentSearchCondition condition) {
    return service.searchStudentsByCondition(condition);
  }


  /**
   * 受講生詳細の検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Operation(
      summary = "受講生検索",
      description = "任意の受講生を検索します。")
  @GetMapping("/students/{id}")
  public StudentDetail getStudent(@PathVariable @Min(1) @Max(999) int id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      summary = "受講生登録",
      description = "受講生を登録します。")
  @PostMapping("/students")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。
   * キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(
      summary = "受講生更新",
      description = "受講生の更新をします。")
  @PutMapping("/students/{id}")
  public ResponseEntity<String> updateStudent(@PathVariable @Min(1) @Max(999) int id, @RequestBody @Valid StudentDetail studentDetail) {
    studentDetail.getStudent().setId(id);
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新完了");
  }
}
