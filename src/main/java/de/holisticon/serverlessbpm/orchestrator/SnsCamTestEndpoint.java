package de.holisticon.serverlessbpm.orchestrator;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cam-test")
@Slf4j
public class SnsCamTestEndpoint {

    private final RuntimeService runtimeService;

    @Autowired
    public SnsCamTestEndpoint(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @NotificationMessageMapping
    public void handleNotificationMessage(@NotificationSubject String executionId, @NotificationMessage String signalName) {
        log.info("Signalling execution ID {} with signal name {}", executionId, signalName);
        runtimeService.signal(executionId, signalName, null, null);
    }


    @NotificationSubscriptionMapping
    public void handleSubscriptionMessage(NotificationStatus status) {
        log.info("Received Subscribtion Message. Confirming...");
        status.confirmSubscription();
    }

    @NotificationUnsubscribeConfirmationMapping
    public void handleUnsubscribeMessage(NotificationStatus status) {
        log.info("Received Unsubscribe Message. Resubscribng...");
        status.confirmSubscription();
    }

}
