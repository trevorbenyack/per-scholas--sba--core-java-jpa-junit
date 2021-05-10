package jpa.util;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class populates the database with seed data
public class InitializerService extends EntityManagerService {

    public void initializeData() {
        EntityManager em = getEntityManager();
        Path sqlFilePath;

        sqlFilePath = Paths.get("src/main/resources/sql-scripts/Course.sql");
        executeQueries(sqlFilePath, em);

        sqlFilePath = Paths.get("src/main/resources/sql-scripts/Student.sql");
        executeQueries(sqlFilePath, em);

    }

    private void executeQueries(Path sqlFilePath, EntityManager em) {
        // try-with-resources
        try (Stream<String> sqlLines = Files.lines(sqlFilePath)) {
            sqlLines.forEach(line -> {
                em.getTransaction().begin();
                em.createNativeQuery(line).executeUpdate();
                em.getTransaction().commit();
            } );
        } catch (IOException e) {
            System.out.println("Could not open file");
        }
    }
}
