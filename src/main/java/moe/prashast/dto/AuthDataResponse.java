package moe.prashast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthDataResponse {

    private Boolean status;
    private JsonNode data;
    private JsonNode  errorDetails;
    private UdiseCodeResponseMetaData metaData;
}
