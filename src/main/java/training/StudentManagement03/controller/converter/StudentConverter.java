package training.StudentManagement03.controller.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import training.StudentManagement03.data.CourseStatus;
import training.StudentManagement03.data.Student;
import training.StudentManagement03.data.StudentCourse;
import training.StudentManagement03.domain.StudentCourseDetail;
import training.StudentManagement03.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、又はその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報をマッピングする。 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
   *
   * @param studentList       受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(
      List<Student> studentList,
      List<StudentCourse> studentCourseList,
      List<CourseStatus> courseStatusList)
  {

    // 1. CourseId → CourseStatus（1件想定）
    Map<Integer, CourseStatus> statusMap = courseStatusList.stream()
        .collect(Collectors.toMap(
            CourseStatus::getCourseId,
            cs -> cs
        ));

    // 2. StudentCourseDetail を作成
    List<StudentCourseDetail> studentCourseDetails = studentCourseList.stream()
        .map(course -> {
          StudentCourseDetail detail = new StudentCourseDetail();
          detail.setStudentCourse(course);

          // CourseId に紐づく status を設定（なければ null）
          detail.setCourseStatus(statusMap.get(course.getId()));

          return detail;
        })
        .toList();

    // 3. StudentDetail を作成
    return studentList.stream()
        .map(student -> {
          StudentDetail sd = new StudentDetail();
          sd.setStudent(student);

          List<StudentCourseDetail> coursesForStudent = studentCourseDetails.stream()
              .filter(d -> d.getStudentCourse().getStudentId() == student.getId())
              .toList();

          sd.setStudentsCourseList(coursesForStudent);
          return sd;
        })
        .toList();
  }
}