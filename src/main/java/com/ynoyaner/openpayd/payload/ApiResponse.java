package com.ynoyaner.openpayd.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.xml.XmlEscapers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse implements Serializable {

    private  Object data;
    private  Boolean success;
    private  LocalDateTime dateTime;
    private  String message;
    private  String errorCode;
    private  String path;

    private Map<String, String> validationErrors;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String data, String message, String errorCode, String path) {
        this.dateTime = LocalDateTime.now();
        this.data = data;
        this.success = success;
        this.message = message;
        this.errorCode=errorCode;
        this.path = path;
    }

    public ApiResponse(Boolean success, String message, String errorCode) {
        this.dateTime=LocalDateTime.now();
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ApiResponse(Boolean success, Object data) {
        this.dateTime = LocalDateTime.now();
        this.data = data;
        this.success = success;
    }
    public Object getData() {
        return data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

}
