package jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EntyMgrUtil {

    static private EntityManagerFactory entityManagerFactory;

    public EntityManager getEntityManager() {

        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("SmsDb");
            initializeData();
        }

        return entityManagerFactory.createEntityManager();
    }

    private void initializeData() {
        EntityManager em = (new EntyMgrUtil()).getEntityManager();
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

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }



}
