package moe.prashast.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

@Getter
@Setter
@Component
public class UdiseValidationServiceBean {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UdiseValidationServiceBean(RestTemplate restTemplate,
                                  ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
}
