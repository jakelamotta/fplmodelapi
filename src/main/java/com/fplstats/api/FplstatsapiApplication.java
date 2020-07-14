package com.fplstats.api;

import com.fplstats.repositories.database.DatabaseUtility;
import com.fplstats.repositories.database.models.League;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class FplstatsapiApplication {

	public static void main(String[] args) {


		EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();

		League league = new League();
		league.setName("Allsvenskan");


		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(league);
		entityManager.getTransaction().commit();
		factory.close();

		//https://spring.io/guides/gs/spring-boot/
		//SpringApplication.run(FplstatsapiApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");



			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
