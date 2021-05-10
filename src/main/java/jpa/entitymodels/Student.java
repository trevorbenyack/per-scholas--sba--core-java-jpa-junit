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
public class Student {

    @Column(name = "email", nullable = false)
    @Id
    String sEmail;

    @Column(name = "name", nullable = false)
    String sName;

    @Column(name = "password", nullable = false)
    String sPass;

    // Eager fetching is used b/c of the structure of the methods in StudentService
    // A join table is used to keep in line with the class structures in the requirements
    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable
    (
        name="student_courses",
        joinColumns=@JoinColumn(name="student_email", referencedColumnName="email"),
        inverseJoinColumns=@JoinColumn(name="course_id", referencedColumnName ="id")
    )
    List<Course> sCourses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!sEmail.equals(student.sEmail)) return false;
        if (!sName.equals(student.sName)) return false;
        return sPass.equals(student.sPass);
    }

    @Override
    public int hashCode() {
        int result = sEmail.hashCode();
        result = 31 * result + sName.hashCode();
        result = 31 * result + sPass.hashCode();
        return result;
    }
}
