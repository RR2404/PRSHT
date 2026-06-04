package moe.prashast.dto;

import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class UdiseCodeResponse {

    private Boolean status;
    private JsonNode data;
    private JsonNode  errorDetails;
    private UdiseCodeResponseMetaData metaData;
}
