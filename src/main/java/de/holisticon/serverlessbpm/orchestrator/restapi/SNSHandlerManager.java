package de.holisticon.serverlessbpm.orchestrator.restapi;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class SNSHandlerManager implements Function<String, Consumer<JsonNode>> {

    public final NotificationHandler NotificationHandler;

    public final SubscriptionConfirmationHandler SubscriptionConfirmationHandler;

    @SneakyThrows
    public Consumer<JsonNode> apply(String messageType) {
        Field field = ReflectionUtils.findField(SNSHandlerManager.class, messageType+"Handler");
        if(field == null) {
            return null;
        } else {
            return (Consumer)field.get(this);
        }
    }
}
