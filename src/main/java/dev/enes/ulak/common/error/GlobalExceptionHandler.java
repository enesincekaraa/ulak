package dev.enes.ulak.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem=ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Doğrulama hatası");
        problem.setDetail("Gönderilen veri geçersiz");
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        problem.setProperty("errors",errors);
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleNotFound(IllegalArgumentException ex) {
        ProblemDetail problem=ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Kaynak bulunamadı");
        problem.setDetail(ex.getMessage());
        return problem;
    }
}
