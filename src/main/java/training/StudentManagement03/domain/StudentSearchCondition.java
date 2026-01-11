package training.StudentManagement03.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentSearchCondition {

  private String name;
  private String area;
  private Integer age;
  private String gender;
}
