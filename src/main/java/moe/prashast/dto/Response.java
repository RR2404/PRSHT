package moe.prashast.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moe.prashast.constant.Messages;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

//    private String url;

    private Integer httpStatus;

    private boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;


    public Response(Messages messages, Map<String, Object> responseData) {
        this.httpStatus = HttpStatus.OK.value();
        this.status = messages.isStatus();
        this.message = messages.getMessage();
        this.data = responseData;
    }

    public Response(ErrorResponse errorResponse) {
        this.httpStatus = HttpStatus.OK.value();
        this.status = false;
        this.error = errorResponse;
        this.data=null;
    }

    public Response(Messages messages) {
        this.httpStatus = HttpStatus.OK.value();
        this.status = messages.isStatus();
        this.message = messages.getMessage();
    }

//    public Response(Map<String, String> captcha, Messages messages) {
//        this.status = messages.isStatus();
//        this.httpStatus = HttpStatus.OK.value();
//        this.message = messages.getMessage();
//        this.data = captcha;
//    }

//    public Response(Messages messages, UdiseCodeResponse responseData) {
//        this.httpStatus = HttpStatus.OK.value();
//        this.status = messages.isStatus();
//        this.message = messages.getMessage();
//        this.data = responseData;
//    }

    public Response(Messages messages, Object responseData) {
        this.httpStatus = HttpStatus.OK.value();
        this.status = messages.isStatus();
        this.message = messages.getMessage();
        this.data = responseData;
    }

    public Response(Messages messages, String responseData) {
        this.httpStatus = HttpStatus.OK.value();
        this.status = messages.isStatus();
        this.message = messages.getMessage() + responseData;
        this.data = responseData;
    }

    public Response(Boolean status, Integer httpStatus, Messages message, Object data) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message.getMessage();
        this.data = data;
    }

    public Response(JsonNode errorDetails) {
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
        this.status = false;
        this.error = errorDetails;
    }

//    public Response(boolean status, int value, tools.jackson.databind.JsonNode errorDetails, tools.jackson.databind.JsonNode errorDetails1) {
//        this.httpStatus=status
//        this.status=false;
//
//    }
}