package com.port.petfit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackPortApplication implements CommandLineRunner{
	@Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BackPortApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {
        System.out.println("Checking database connection...");
        try {
            dataSource.getConnection();
            System.out.println("Database connected successfully.");
            System.out.println("Executing a sample query...");
            int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM petfit_admin", Integer.class);
            System.out.println("Total records in your_table_name: " + count);
        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

}