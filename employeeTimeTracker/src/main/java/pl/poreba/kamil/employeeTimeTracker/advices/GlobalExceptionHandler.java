package pl.poreba.kamil.employeeTimeTracker.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.poreba.kamil.employeeTimeTracker.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNoResurceNotFound(ResourceNotFoundException exception) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Resource not found")
                .build();
        return buildAPIResponseError(error);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleFieldValidation(MethodArgumentNotValidException exception) {
        List<String> validationErrorMessages = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation faild")
                .subErrors(validationErrorMessages)
                .build();
        return buildAPIResponseError(error);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception exception) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildAPIResponseError(error);
    }

    private ResponseEntity<ApiResponse<?>> buildAPIResponseError(ApiError error) {
        return new ResponseEntity<>(new ApiResponse<>(error),error.getStatus());
    }
}
