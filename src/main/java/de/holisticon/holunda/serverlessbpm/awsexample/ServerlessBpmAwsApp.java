package de.holisticon.holunda.serverlessbpm.awsexample;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableProcessApplication
@EnableContextInstanceData
public class ServerlessBpmAwsApp {

  public static void main(String[] args) {
    SpringApplication.run(ServerlessBpmAwsApp.class, args);
  }
}
