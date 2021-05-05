package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j
public class Course {

    @Id
    int courseId;

    String courseName;

    String courseInstructor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (courseId != course.courseId) return false;
        if (!courseName.equals(course.courseName)) return false;
        return courseInstructor.equals(course.courseInstructor);
    }

    @Override
    public int hashCode() {
        int result = courseId;
        result = 31 * result + courseName.hashCode();
        result = 31 * result + courseInstructor.hashCode();
        return result;
    }
}
