package de.holisticon.serverlessbpm.orchestrator;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SNSConfiguration {

    @Bean
    public AmazonSNS snsClient() {
        return AmazonSNSClientBuilder.defaultClient();
    }
}
