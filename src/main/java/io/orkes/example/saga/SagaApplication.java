package io.orkes.example.saga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.orkes.conductor.client.ApiClient;
import io.orkes.conductor.client.TaskClient;
import io.orkes.conductor.client.WorkflowClient;
import io.orkes.conductor.client.http.OrkesTaskClient;
import io.orkes.conductor.client.http.OrkesWorkflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@AllArgsConstructor
@SpringBootApplication(scanBasePackages = {"com.netflix.conductor", "io.orkes.example"})
@Slf4j
public class SagaApplication {
	private final Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SagaApplication.class, args);
	}

	@Bean
	public ApiClient apiClient() {
		String key = env.getProperty("orkes.access.key");
		String secret = env.getProperty("orkes.access.secret");
		String conductorServer = env.getProperty("orkes.conductor.server.url");

		log.info("Connecting to conductor server {} using key {}", conductorServer, key);

		// If authentication is disabled key and secret can be null
		return new ApiClient(Objects.requireNonNull(conductorServer), key, secret);
	}

	@Bean
	public TaskClient getTaskClient(ApiClient apiClient) {
		return new OrkesTaskClient(apiClient);
	}

	@Bean
	public WorkflowClient getWorkflowClient(ApiClient apiClient) {
		return new OrkesWorkflowClient(apiClient);
	}

}
