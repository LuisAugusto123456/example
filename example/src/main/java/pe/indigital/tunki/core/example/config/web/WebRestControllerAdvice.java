package pe.indigital.tunki.core.example.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.indigital.tunki.core.example.util.exception.EntityNotFoundException;
import pe.indigital.tunki.core.example.util.exception.EntityValidationWrapper;
import pe.indigital.tunki.core.example.util.exception.GeneralException;
import pe.indigital.tunki.core.example.util.logger.WebLogger;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebRestControllerAdvice extends WebResponseBodyAdvice {

    @Autowired
    public WebRestControllerAdvice(WebLogger webLogger) {
        super(webLogger);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleNotFoundException(GeneralException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(EntityValidationWrapper.builder().build().buildFromError(ex));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityValidationWrapper.builder().build().buildFromError(ex));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity handleMethodArgumentNotValid(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(EntityValidationWrapper.builder().build().buildFromError(ex));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity handleMethodArgumentNotValid(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(EntityValidationWrapper.builder().build().buildFromError(ex));
    }

}