/*package fr.uga.miage.m1.my_projet_g1_10.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prisonniers"); // URL de la base de données
        dataSource.setUsername("root"); // Nom d'utilisateur
        dataSource.setPassword(""); // Mot de passe
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Driver MySQL
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("fr.uga.miage.m1.my_projet_g1_10.model"); // Package contenant vos entités
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Configuration d'Hibernate
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update"); // Gérer les migrations (ex: update, validate, etc.)
        jpaProperties.put("hibernate.show_sql", "true"); // Afficher les requêtes SQL dans la console
        emf.setJpaProperties(jpaProperties);

        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
*/