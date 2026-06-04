package moe.prashast.dto;

import lombok.Data;

@Data
public class UdiseCodeResponseMetaData {

    private String apiCode;
    private String apiVersion;
    private String requestTimestamp;
    private String responseTimestamp;
    private String transactionId;

    private Integer pageNo;
    private Integer totalPages;
    private Integer recordsPerPage;
    private Integer totalRecords;
}
