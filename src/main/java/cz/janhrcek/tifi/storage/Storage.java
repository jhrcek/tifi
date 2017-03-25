package cz.janhrcek.tifi.storage;

import cz.janhrcek.tifi.model.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Storage {

    private SessionFactory sessionFactory = createSessionFactory();


    public void addExpense(Expense e) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
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
