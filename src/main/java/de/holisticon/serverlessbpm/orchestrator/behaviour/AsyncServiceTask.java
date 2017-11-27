package de.holisticon.serverlessbpm.orchestrator.behaviour;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.camunda.bpm.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AsyncServiceTask extends AbstractBpmnActivityBehavior {

    private static final String EXECUTION_ID = "executionId";
    private static final String ECHO_ARN = "arn:aws:sns:eu-central-1:831064628565:cam-test";
    private static final String MSG = "Hello World";

    @Autowired
    private AmazonSNS snsClient;

    public void execute(final ActivityExecution execution) throws Exception {

        // Build the payload for the message:
        Map<String, Object> payload = new HashMap<String, Object>(execution.getVariables());
        // Add the execution id to the payload:
        payload.put(EXECUTION_ID, execution.getId());

        // Publish a message to the outbound message queue. This method returns after the message has
        // been put into the queue. The actual service implementation (Business Logic) will not yet
        // be invoked:
        PublishRequest publishRequest = new PublishRequest(ECHO_ARN, MSG, execution.getId());
        PublishResult publishResult = snsClient.publish(publishRequest);
        System.out.println("MessageId - " + publishResult.getMessageId());

        /*

        processEngine.getRuntimeService().signal(executionId, callbackPayload);
         */
    }

    @Override
    public void signal(ActivityExecution execution, String signalName, Object signalData) throws Exception {

        // leave the service task activity:
        leave(execution);
    }

}