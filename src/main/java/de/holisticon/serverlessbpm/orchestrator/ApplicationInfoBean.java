package de.holisticon.serverlessbpm.orchestrator;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ToString
@Slf4j
public class ApplicationInfoBean {

    @Value("${ami-id:N/A}")
    private String amiId;

    @Value("${hostname:N/A}")
    private String hostname;

    @Value("${instance-type:N/A}")
    private String instanceType;

    @Value("${services/domain:N/A}")
    private String serviceDomain;

    @PostConstruct
    public void dump() {
      log.info("Application Info {}", this.toString());
    }
}
