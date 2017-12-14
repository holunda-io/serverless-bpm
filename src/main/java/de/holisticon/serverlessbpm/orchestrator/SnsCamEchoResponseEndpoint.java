package de.holisticon.serverlessbpm.orchestrator;

import com.amazonaws.services.sns.AmazonSNS;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static de.holisticon.serverlessbpm.orchestrator.SnsCamEchoResponseEndpoint.SNS_EP_CAM_TEST;

@Controller
@RequestMapping(SNS_EP_CAM_TEST)
@Slf4j
public class SnsCamEchoResponseEndpoint implements ApplicationListener<ApplicationReadyEvent> {

    public static final String SNS_EP_CAM_TEST = "/cam-echo-response";
    public static final String SNS_TOPIC_CAM_ECHO_RESPONSE = "arn:aws:sns:eu-central-1:831064628565:cam-echo-response";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplicationInfoBean appInfo;

    @Autowired
    private AmazonSNS amazonSns;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        String url = "http://" + appInfo.getPublicHostname() + "/" + SnsCamEchoResponseEndpoint.SNS_EP_CAM_TEST;
        log.info("Subscribing to topic {} with http endpoint {}", SNS_TOPIC_CAM_ECHO_RESPONSE, url);
        amazonSns.subscribe(SNS_TOPIC_CAM_ECHO_RESPONSE,"http", url);
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
