package training.StudentManagement03.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
            .andExpect(status().isOk());

    verify(service, times(1)).searchStudentsList();
  }

  @Test
  void 受講生詳細のID検索が実行できて空のリストが返ってくること() throws Exception {
    int id = 1;
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録が実行できて空のリストで返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                      "student" : {
                          "name" : "コントローラーテスト",
                          "kanaName" : "コントローラーテスト",
                          "nickname" : "テスト",
                          "email" : "test@gamil.com",
                          "area" : "北京",
                          "age" : 10,
                          "gender" : "男性",
                          "remark" : ""
                      },
                      "studentsCourseList" : [
                          {
                              "courseName" : "テストコース"
                          }
                      ]
                    }
                """
            ))
            .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空のリストで返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                      "student" : {
                          "id" : 1,
                          "name" : "コントローラーテスト",
                          "kanaName" : "コントローラーテスト",
                          "nickname" : "テスト",
                          "email" : "test@gamil.com",
                          "area" : "北京",
                          "age" : 10,
                          "gender" : "男性",
                          "remark" : ""
                      },
                      "studentsCourseList" : [
                          {
                              "courseName" : "テストコース"
                          }
                      ]
                    }
                """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setName("abcdefg");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生で名前を設定した文字数以上にした際に入力チェックにかかること() {
    Student student = new Student();
    student.setName("abcdefghijklmnopqrstuvwxyz");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message").containsOnly("名前は20文字以内で入力してください");
  }
}