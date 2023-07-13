package io.orkes.example.saga;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.common.config.ObjectMapperProvider;
import io.orkes.example.saga.dao.BaseDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import lombok.AllArgsConstructor;


import java.util.Objects;

@AllArgsConstructor
@SpringBootApplication
@ComponentScan(basePackages = {"io.orkes"})
public class SagaApplication {

	private static final BaseDAO db = new BaseDAO("jdbc:sqlite:cab_saga.db");

	public static void main(String[] args) {
		SpringApplication.run(SagaApplication.class, args);
		initDB();
	}

	public static void initDB() {
		db.createTables("booking");
		db.createTables("cab_assignment");
		db.createTables("payment");
	}
}
