package com.fplstats.api;

import com.fplstats.repositories.database.DatabaseUtility;
import com.fplstats.repositories.database.models.League;
import com.fplstats.repositories.database.models.Season;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@SpringBootApplication
public class FplstatsapiApplication {

	public static void main(String[] args) {


		EntityManagerFactory factory = DatabaseUtility.getEntityManagerFactory();

		EntityManager entityManager = factory.createEntityManager();


		Query query = entityManager.createQuery("from Season As S where S.startYear = 2019");

		List list = query.getResultList();

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
