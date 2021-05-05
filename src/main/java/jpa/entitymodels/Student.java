package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Log4j
public class Student {

    @Column(nullable = false)
    @Id
    String studentEmail;

    @Column(nullable = false)
    String studentName;

    @Column(nullable = false)
    String studentPassword;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn
    List<Course> sCourses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!studentEmail.equals(student.studentEmail)) return false;
        if (!studentName.equals(student.studentName)) return false;
        return studentPassword.equals(student.studentPassword);
    }

    @Override
    public int hashCode() {
        int result = studentEmail.hashCode();
        result = 31 * result + studentName.hashCode();
        result = 31 * result + studentPassword.hashCode();
        return result;
    }
}
