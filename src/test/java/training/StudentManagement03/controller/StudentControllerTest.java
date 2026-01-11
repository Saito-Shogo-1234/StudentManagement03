package training.StudentManagement03.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import training.StudentManagement03.domain.StudentDetail;
import training.StudentManagement03.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void getStudents_正常に一覧取得できる() throws Exception {
    when(service.searchStudentsByCondition(any()))
        .thenReturn(List.of());

    mockMvc.perform(get("/students"))
        .andExpect(status().isOk());

    verify(service).searchStudentsByCondition(any());
  }

  @Test
  void getStudent_ID指定で取得できる() throws Exception {
    int id = 1;

    when(service.searchStudent(id)).thenReturn(new StudentDetail());

    mockMvc.perform(get("/students/{id}", id))
        .andExpect(status().isOk());

    verify(service).searchStudent(id);
  }

  @Test
  void getStudent_IDが不正な場合_400になる() throws Exception {
    mockMvc.perform(get("/students/{id}", 0))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerStudent_正常に登録できる() throws Exception {
    when(service.registerStudent(any()))
        .thenReturn(new StudentDetail());

    mockMvc.perform(post("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "student": {
                    "name": "テスト",
                    "kanaName": "テスト",
                    "nickname": "test",
                    "email": "test@gmail.com",
                    "area": "東京",
                    "age": 20,
                    "gender": "男性",
                    "remark": ""
                  },
                  "studentCourseList": [
                    { "courseName": "Java" }
                  ]
                }
                """))
        .andExpect(status().isOk());

    verify(service).registerStudent(any());
  }

  @Test
  void registerStudent_不正な入力の場合_400になる() throws Exception {
    mockMvc.perform(post("/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "student": {
                    "name": "",
                    "email": "invalid"
                  }
                }
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateStudent_正常に更新できる() throws Exception {
    int id = 1;

    mockMvc.perform(put("/students/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "student": {
                    "name": "更新テスト"
                  },
                  "studentCourseList": []
                }
                """))
        .andExpect(status().isOk());

    verify(service).updateStudent(any());
  }
}