package cz.janhrcek.tifi.storage;

import cz.janhrcek.tifi.model.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {

    final private SessionFactory sessionFactory = createSessionFactory();


    public void addExpense(Expense e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
        }
    }

    public List<Expense> getExpenses() {
        try (Session session = sessionFactory.openSession()) {
            Query q = session.createQuery("from Expense");
            return (List<Expense>) q.getResultList();
        }
    }

    public void close() {
        sessionFactory.close();
    }

    private SessionFactory createSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .applySetting("hibernate.connection.url", getConnectionUrl()) // Look for DB in current working dir
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private String getConnectionUrl() {
        String workingDir = System.getProperty("user.dir");
        Path jdbcPath = Paths.get(workingDir).resolve("tifi").toAbsolutePath();
        Path dbFile = Paths.get(workingDir).resolve("tifi.mv.db").toAbsolutePath();
        if (Files.exists(dbFile)) {
            System.out.println("Existing DB file found at " + dbFile);
        } else {
            System.out.println("Creating new DB file at " + dbFile);
        }
        return "jdbc:h2:" + jdbcPath.toString();
    }
}
