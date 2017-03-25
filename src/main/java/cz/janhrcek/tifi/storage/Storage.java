package cz.janhrcek.tifi.storage;

import cz.janhrcek.tifi.model.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
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
                .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
