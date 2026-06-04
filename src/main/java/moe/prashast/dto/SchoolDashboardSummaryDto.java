package moe.prashast.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
@Data
public class SchoolDashboardSummaryDto {

    private Object summary;
    private Object broadCategory;
    private Object broadManagement;
    private Object ruralUrban;
    private Object hierarchy;
    private Object execStatus;
    private Object errorDescription;

}
