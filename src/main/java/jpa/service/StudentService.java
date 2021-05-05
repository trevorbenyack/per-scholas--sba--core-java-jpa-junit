package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public class StudentService implements StudentDAO {
    @Override
    public List<Student> getAllStudents() {
        return null;
    }

    @Override
    public Student getStudentByEmail(String sEmail) {
        return null;
    }

    @Override
    public boolean validateStudent(String sEmail, String sPassword) {
        return false;
    }

    @Override
    public List<Course> getStudentCourses(String sEmail) {
        return null;
    }

    @Override
    public void registerStudentToCourse(String studentEmail, Course course) {

    }
}
