package de.holisticon.serverlessbpm.orchestrator;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EchoFunctionDelegate extends AbstractBpmnActivityBehavior {

    public static final String ECHO_ARN = "arn:aws:sns:eu-central-1:831064628565:cam-test";

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public EchoFunctionDelegate(AmazonSNS amazonSns) {
        notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSns);
    }

    public void execute(final ActivityExecution execution) throws Exception {

        // Publish a message to the outbound message queue. This method returns after the message has
        // been put into the queue. The actual service implementation (Business Logic) will not yet
        // be invoked:
        notificationMessagingTemplate.sendNotification(ECHO_ARN, "Hello World", execution.getId());
    }

    @Override
    public void signal(ActivityExecution execution, String signalName, Object signalData) throws Exception {

        // leave the service task activity:
        leave(execution);
    }

}