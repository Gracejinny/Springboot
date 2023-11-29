package com.github.supercoding.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.github.supercoding.respository.airlineTicket", "com.github.supercoding.respository.flight",
                "com.github.supercoding.respository.passenger", "com.github.supercoding.respository.reservations",
                "com.github.supercoding.respository.users","com.github.supercoding.respository.roles", "com.github.supercoding.respository.userPrincipal"},
        entityManagerFactoryRef = "entityManagerFactoryBean2",
        transactionManagerRef = "tmJpa2"
)
public class JpaConfig2 {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean2(@Qualifier("dataSource2")DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.github.supercoding.respository.airlineTicket", "com.github.supercoding.respository.flight",
                "com.github.supercoding.respository.passenger", "com.github.supercoding.respository.reservations",
                "com.github.supercoding.respository.users", "com.github.supercoding.respository.roles", "com.github.supercoding.respository.userPrincipal");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comment", "true");

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "tmJpa2")
    public PlatformTransactionManager transactionManager2(@Qualifier("dataSource2") DataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean2(dataSource).getObject());
        return transactionManager;
    }
}
