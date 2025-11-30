package training.StudentManagement03.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import training.StudentManagement03.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(14);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("試験");
    student.setKanaName("シケン");
    student.setNickname("テスト");
    student.setEmail("shiken@gmail.com");
    student.setArea("東京");
    student.setAge(15);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(15);
  }
}