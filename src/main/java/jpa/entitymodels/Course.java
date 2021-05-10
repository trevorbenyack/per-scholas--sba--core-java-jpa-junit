package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id @Column(name = "id")
    int cId;

    @Column(name = "name")
    String cName;

    @Column(name = "instructor")
    String cInstructorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (cId != course.cId) return false;
        if (!cName.equals(course.cName)) return false;
        return cInstructorName.equals(course.cInstructorName);
    }

    @Override
    public int hashCode() {
        int result = cId;
        result = 31 * result + cName.hashCode();
        result = 31 * result + cInstructorName.hashCode();
        return result;
    }
}
