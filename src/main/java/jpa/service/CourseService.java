package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.util.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CourseService extends EntityManagerService implements CourseDAO {
    @Override
    public List<Course> getAllCourses() {
        List<Course> coursesList = null;
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);
        criteriaQuery.select(root);

        try {
            coursesList = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            System.out.println("There was an error trying to retrieve the list of courses.");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return coursesList;
    } // end getAllCourses() method

    @Override
    public Course getCourseById(int courseId) {

        Course course = null;
        EntityManager entityManager = getEntityManager();

        try {
            course = entityManager.find(Course.class, courseId);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error trying to find that course");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        return course;
    } // end getCourseById() method
}
