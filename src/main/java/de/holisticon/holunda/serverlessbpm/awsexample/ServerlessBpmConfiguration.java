package de.holisticon.holunda.serverlessbpm.awsexample;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static de.holisticon.holunda.serverlessbpm.awsexample.ServerlessBpmConfiguration.PREFIX;

@Configuration
@ConfigurationProperties(prefix = PREFIX)
@Getter
@Setter
@Slf4j
public class ServerlessBpmConfiguration {

    public final static String PREFIX = "application";
    private Aws aws;

    @PostConstruct
    public void dumpConfig() {
        log.info("Configuration:\n{}:{}", PREFIX, this);
    }

    @Override
    public String toString() {
        return "\n\taws:" + aws;
    }

    @Getter
    @Setter
    public static class Aws {

        private Topic topic;

        @Override
        public String toString() {
            return "\n\t\ttopic:" + topic;
        }

        @Getter
        @Setter
        public static class Topic {
            private String publishArn;
            private String subscribeArn;

            @Override
            public String toString() {
                return "\n\t\t\tpublishArn: " + publishArn +
                        "\n\t\t\tpublishArn: " + subscribeArn;
            }
        }
    }

}
