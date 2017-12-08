package de.holisticon.serverlessbpm.orchestrator;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;

@SpringBootApplication
@EnableProcessApplication
@EnableContextInstanceData
public class OrchestratorApp {

  public static void main(String[] args) {
    SpringApplication.run(OrchestratorApp.class, args);
  }
}
