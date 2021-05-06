package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.util.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CourseService extends EntityManagerService implements CourseDAO {
    @Override
    public List<Course> getAllCourses() {
        List<Course> coursesList;
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> root = criteriaQuery.from(Course.class);
        criteriaQuery.select(root);

        coursesList = entityManager.createQuery(criteriaQuery).getResultList();
        entityManager.close();

        return coursesList;
    }

    @Override
    public Course GetCourseById(int courseId) {

        Course course;
        EntityManager entityManager = getEntityManager();

        course = entityManager.find(Course.class, courseId);

        entityManager.close();
        return course;
    }
}
