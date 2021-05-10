import jpa.entitymodels.Student;
import jpa.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentServiceTest {

    @Test
    void testGetStudentByEmail() {

        Student expected = new Student("aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j", null);
        Student actual = new StudentService().getStudentByEmail("aiannitti7@is.gd");

        Assertions.assertEquals(expected, actual, "The method should retrieve the specified student");

    }

}
