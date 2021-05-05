package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> getAllStudents();
    Student getStudentByEmail(String sEmail);
    boolean validateStudent(String sEmail, String sPassword);
    List<Course> getStudentCourses(String sEmail);
    void registerStudentToCourse(String studentEmail, Course course);
}
