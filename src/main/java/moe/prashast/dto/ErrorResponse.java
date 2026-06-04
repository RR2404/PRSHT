package moe.prashast.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moe.prashast.constant.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorId;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public ErrorResponse(Messages messages) {
        this.httpStatus= HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.status=messages.isStatus();
        this.message=messages.getMessage();
        this.type=messages.getType();
        this.errorId= LocalDateTime.now().format(FORMATTER);

    }

    public ErrorResponse(String messages) {
        this.httpStatus= HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message=messages;
//        this.type=messages;
        this.errorId= LocalDateTime.now().format(FORMATTER);

    }

    public ErrorResponse(UsernameNotFoundException e) {
        this.message=e.getMessage();
        this.errorId= LocalDateTime.now().format(FORMATTER);
    }

    public ErrorResponse(Messages messages, String validationMessage) {
        this.type = messages.getType();
//        this.status = messages.isStatus();
        this.message = validationMessage;
    }
}
