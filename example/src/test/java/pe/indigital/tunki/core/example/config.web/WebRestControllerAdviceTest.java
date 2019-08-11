package pe.indigital.tunki.core.example.config.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pe.indigital.tunki.core.example.entity.Example;
import pe.indigital.tunki.core.example.util.exception.EntityNotFoundException;
import pe.indigital.tunki.core.example.util.logger.WebLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebRestControllerAdviceTest {

    @Mock
    private WebLogger webLogger;

    private WebRestControllerAdvice webRestControllerAdvice;

    @Mock
    private MethodParameter parameter;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        webRestControllerAdvice = new WebRestControllerAdvice(webLogger);
    }

    @Test
    public void handleNotFoundException_ShouldReturnResponseEntity_WhenHttpStatusIsNotFound() {
        String exampleId = UUID.randomUUID().toString();
        EntityNotFoundException entityNotFoundException=
            new EntityNotFoundException(Example.class, exampleId);
        ResponseEntity result = webRestControllerAdvice.handleNotFoundException(entityNotFoundException);
        assertEquals(404, result.getStatusCode().value(), 0);
    }

    @Test
    public void handleMethodArgumentNotValid_ShouldReturnResponseEntity_WhenHttpStatusIsBadRequest() {
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError( "Example","exampleId", "Not Valid"));
        when(bindingResult.getAllErrors()).thenReturn(errors);
        MethodArgumentNotValidException methodArgumentNotValidException
            = new MethodArgumentNotValidException(parameter, bindingResult);
        ResponseEntity result = webRestControllerAdvice.handleMethodArgumentNotValid(methodArgumentNotValidException);
        assertEquals(400, result.getStatusCode().value(), 0);
    }

    @Test
    public void handleHttpMediaTypeNotSupported_ShouldReturnResponseEntity_WhenHttpStatusIsBadRequest() {
        HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException =
            new HttpMediaTypeNotSupportedException(new MediaType("''"), new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }});
        ResponseEntity result = webRestControllerAdvice.handleMethodArgumentNotValid(httpMediaTypeNotSupportedException);
        assertEquals(415, result.getStatusCode().value(), 0);
    }

    @Test
    public void handleHttpMessageNotReadable_ShouldReturnResponseEntity_WhenHttpStatusIsBadRequest() {
        HttpMessageNotReadableException httpMessageNotReadableException = mock(HttpMessageNotReadableException.class);
        ResponseEntity result = webRestControllerAdvice.handleMethodArgumentNotValid(httpMessageNotReadableException);
        assertEquals(400, result.getStatusCode().value(), 0);
    }
}
