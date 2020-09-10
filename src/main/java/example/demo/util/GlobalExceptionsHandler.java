package example.demo.util;

import example.demo.exceptions.ClientExistsException;
import example.demo.exceptions.EntityNotFoundException;
import example.demo.exceptions.NoValuePresentException;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ApiError serverErrorHandling(Exception exception) {
        StringWriter errors = new StringWriter();
        exception.printStackTrace(new PrintWriter(errors));
        ApiError apiError = ApiError.builder().status(500).body(errors.toString()).build();
        errorLog(apiError);
        return apiError;
    }

    @ExceptionHandler(ClientExistsException.class)
    public @ResponseBody
    ApiError handlingUserNotFoundException(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           ClientExistsException clientExistsException) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiError.builder().status(HttpStatus.BAD_REQUEST.value()).body(clientExistsException.getMessage()).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public @ResponseBody
    ApiError handlingUserNotFoundException(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           EntityNotFoundException entityNotFoundException) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ApiError.builder().status(HttpStatus.NOT_FOUND.value()).body(entityNotFoundException.getMessage()).build();
    }

    @ExceptionHandler(NoValuePresentException.class)
    public @ResponseBody
    ApiError handlingNoValuePresentException(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           NoValuePresentException noValuePresentException) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiError.builder().status(HttpStatus.BAD_REQUEST.value()).body(noValuePresentException.getMessage()).build();
    }

    public void errorLog(ApiError apiError) {
        log.error(apiError.toString());
    }

    @Builder
    @Data
    @ToString
    public static class ApiError {
        int status;
        String body;
    }
}
