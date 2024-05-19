import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;


public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties properties = new Properties();
//            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//            properties.put(Environment.URL, "jdbc:mysql://localhost:3305/trpp_project_db");
//            properties.put(Environment.USER, "root");
//            properties.put(Environment.PASS, "root");
            properties.put(Environment.DRIVER, "org.postgresql.Driver");
            properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
            properties.put(Environment.USER, "postgres");
            properties.put(Environment.PASS, "changemeinprod!");
            properties.put(Environment.HBM2DDL_AUTO, "update");

            sessionFactory = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
