package moe.prashast.dto;
import lombok.Data;

@Data
public class ErrorDetails {

    private String type;
    private String message;
    private String responseCode;
}
