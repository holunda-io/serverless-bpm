package de.holisticon.serverlessbpm.orchestrator.restapi;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class NotificationHandler implements Consumer<JsonNode> {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void accept(JsonNode snsMessage) {
        JsonNode subject = snsMessage.get("Subject");
        JsonNode message = snsMessage.get("Message");
        if (subject == null || message == null || !subject.isTextual() || !message.isTextual()) {
            log.error("Invalid Notification, fields Subject or Message not found or not text!");
        } else {
            String executionId = subject.textValue();
            String signalName = message.textValue();
            log.info("Signalling execution ID {} with signal name {}", executionId, signalName);
            runtimeService.signal(executionId, signalName, null, null);
        }
    }
}
