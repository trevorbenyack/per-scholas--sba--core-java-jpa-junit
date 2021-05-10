package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface StudentDAO {

    List<Student> getAllStudents();
    Student getStudentByEmail(String studentEmail);
    boolean validateStudent(String studentEmail, String enteredPassword);
    List<Course> getStudentCourses(String studentEmail);
    void registerStudentToCourse(String studentEmail, Course course);
}
