package de.holisticon.serverlessbpm.orchestrator.restapi;

import com.amazonaws.services.sns.AmazonSNS;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class SubscriptionConfirmationHandler implements Consumer<JsonNode> {

    @Autowired
    private AmazonSNS snsClient;

    @Override
    public void accept(JsonNode snsMessage) {
        JsonNode topicArn = snsMessage.get("TopicArn");
        JsonNode token = snsMessage.get("Token");
        if (topicArn == null || token == null || !topicArn.isTextual() || !token.isTextual() ) {
            log.error("Invalid SubscriptionConfirmation, fields TopicArn or Token not found or not text!");
        } else {
            log.info("Confirming subscription for topic ARN {} and token {}", topicArn.textValue(), token.textValue());
            snsClient.confirmSubscription(topicArn.textValue(), token.textValue());
        }
    }

}
