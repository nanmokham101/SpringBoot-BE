package com.spcourse.springboot2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
//        (
//        exclude = {
//                DataSourceAutoConfiguration.class,
//                HibernateJpaAutoConfiguration.class
//        }
//)
public class Springboot2026Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2026Application.class, args);
	}

}
