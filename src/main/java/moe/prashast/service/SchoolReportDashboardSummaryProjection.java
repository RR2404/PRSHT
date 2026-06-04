package moe.prashast.service;

public interface SchoolReportDashboardSummaryProjection {

    String getSummaryJson();
    String getBroadCategoryJson();
    String getBroadManagementJson();
    String getRuralUrbanJson();
    String getHierarchyJson();
    String getExecStatus();
    String getErrorDescription();

}
