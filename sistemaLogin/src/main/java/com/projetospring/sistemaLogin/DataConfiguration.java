package com.projetospring.sistemaLogin;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
public class DataConfiguration {
    @Bean
    public DataSource dataSource(){

        // criando objeto para conexão direta com banco de dados
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // definindo driver JDBC
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // url do banco de dados (no meu caso porta 3306)
        dataSource.setUrl("jdbc:mysql://localhost:3306/basedata?useSSL=false&serverTimezone=UTC");
        // username usado no banco
        dataSource.setUsername("root");
        // senha do banco
        dataSource.setPassword("#18112006lF");

        return dataSource;
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
    
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        // definindo o sgbd
        adapter.setDatabase(Database.MYSQL);
        // mostra sql no console
        adapter.setShowSql(true);
        // atualiza tabelas automaticamente
        adapter.setGenerateDdl(true);
        // dialeto mysql
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
    
        adapter.setPrepareConnection(true);
        
        return adapter;
    }


}
