package com.ynoyaner.openpayd.exception.handler;

import com.ynoyaner.openpayd.constants.ApplicationConstants;
import com.ynoyaner.openpayd.exception.MissingParameterException;
import com.ynoyaner.openpayd.exception.FixerIOApiException;
import com.ynoyaner.openpayd.exception.ResourceNotFoundException;
import com.ynoyaner.openpayd.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(MissingParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleMissingParameterException(MissingParameterException ex, WebRequest request) {
        return new ApiResponse(false, ex.getErrorMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ApiResponse(false, ex.getErrorMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleHttpMessageNotReadbleError(HttpMessageNotReadableException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), ApplicationConstants.ErrorCodes.INVALID_PARAMETER, resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleMethodArgumentNotValidError(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        Map<String, String> validationErrors = new HashMap<>();
        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;
            validationErrors.put(fieldError.getField(), error.getDefaultMessage());
        }
        ApiResponse apiResponse = new ApiResponse(false, "Validation Errors", ex.getClass().getName(), ApplicationConstants.ErrorCodes.INVALID_PARAMETER,resolvePathFromWebRequest(request));
        apiResponse.setValidationErrors(validationErrors);
        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleMethodArgumentNotValidError(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), ApplicationConstants.ErrorCodes.INVALID_PARAMETER, resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(FixerIOApiException.class)
    @ResponseBody
    public ApiResponse handleFixerIOApiError(FixerIOApiException ex,WebRequest request) {
       return new ApiResponse(false,ex.getMessage(),ex.getCode());
    }

    private String resolvePathFromWebRequest(WebRequest request) {
        try {

            return ((ServletWebRequest) request).getRequest().getRequestURI() != null ?
                    ((ServletWebRequest) request).getRequest().getRequestURI() :
                    ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
