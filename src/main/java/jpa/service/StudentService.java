package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.EntityManagerService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentService extends EntityManagerService implements StudentDAO {

    // returns a list of all students in the database
    @Override
    public List<Student> getAllStudents() {

        List<Student> studentsList = null;
        EntityManager entityManager  = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root);

        try {
            studentsList = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (IllegalArgumentException | PersistenceException | IllegalStateException e) {
            System.out.println("An error occured getting the list of students");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return studentsList;
    }

    // Returns a single student object based on the email address provided
    @Override
    public Student getStudentByEmail(String studentEmail) {

        EntityManager entityManager = getEntityManager();

        Student student = null;
        try {
            student = entityManager.find(Student.class, studentEmail);
        } catch (IllegalArgumentException e) {
            System.out.println("Student email not valid.");
        } finally {
            entityManager.close();
        }

        return student;
    } // end getStudentByEmail() method

    // Validates that the password entered for the student matches that in the database
    @Override
    public boolean validateStudent(String studentEmail, String enteredPassword) {

        Student student = getStudentByEmail(studentEmail);

        return student != null && student.getSPass().equals(enteredPassword);
    } // end validateStudent() method

    // Returns all the courses that a student is enrolled in
    @Override
    public List<Course> getStudentCourses(String studentEmail) {

        Student student = getStudentByEmail(studentEmail);

        return student.getSCourses();
    } // end getStudentCourses() method

    // Registers a course to the logged-in student
    @Override
    public void registerStudentToCourse(String studentEmail, Course course) {

        Student student = null;

        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        try {
            student = entityManager.find(Student.class, studentEmail);

            List<Course> studentCourses = student.getSCourses();

            if (!studentCourses.contains(course)) {
                studentCourses.add(course);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error retrieving the student");
            e.printStackTrace();
        }

        try {
            entityManager.persist(student);
            entityManager.getTransaction().commit();
        } catch (IllegalArgumentException | PersistenceException e) {
            System.out.println("There was an error updating the student");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    } // end registerStudentToCourse() method
} // end StudentService class
