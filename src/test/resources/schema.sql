CREATE TABLE students (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  kana_name VARCHAR(100),
  nick_name VARCHAR(100),
  email VARCHAR(100),
  area VARCHAR(100),
  age INT,
  gender VARCHAR(100),
  remark VARCHAR(100),
  is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE students_courses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  student_id INT NOT NULL,
  course_name VARCHAR(100) NOT NULL,
  course_start TIMESTAMP NULL,
  course_end TIMESTAMP NULL,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

