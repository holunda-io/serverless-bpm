package de.holisticon.serverlessbpm.orchestrator.restapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.function.Consumer;

@RestController
@Path("sns")
@Slf4j
@RequiredArgsConstructor
public class SNSEndpoint {

    private final SNSHandlerManager snsHandlerManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @POST
    @SneakyThrows
    public void receive(@HeaderParam("x-amz-sns-message-type") String messageType, String requestBody) {
        log.info("Received messageType {} with payload\n{}", messageType, requestBody);
        //TODO verify sig
        Consumer<JsonNode> snsHandler = snsHandlerManager.apply(messageType);
        if(snsHandler == null) {
            log.warn("Unknown messageType received: {}", messageType);
        } else {
            JsonNode snsMessage = objectMapper.readTree(requestBody);
            log.info("Processing SNS message with {}", snsHandler);
            snsHandler.accept(snsMessage);
        }
    }

}
