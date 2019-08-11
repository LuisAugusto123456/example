package pe.indigital.tunki.core.example.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.ArrayList;
import java.util.List;
import static pe.indigital.tunki.core.example.util.exception.GenericErrors.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityValidationWrapper {

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private String code;

    @JsonProperty("errors")
    private List<FieldValidationWrapper> errors;

    public EntityValidationWrapper buildFromError(MethodArgumentNotValidException methodArgumentNotValidException) {
        EntityValidationWrapper entityValidationWrapper = new EntityValidationWrapper();
        entityValidationWrapper.setEntity(methodArgumentNotValidException.getBindingResult().getObjectName());
        entityValidationWrapper.setMessage(GE_01.getMessage());
        entityValidationWrapper.setCode(GE_01.name());
        List<FieldValidationWrapper> errorList = new ArrayList<>();
        for (ObjectError objectError : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            errorList.add(
                    new FieldValidationWrapper(((FieldError) objectError).getField(), objectError.getDefaultMessage()));
        }
        entityValidationWrapper.setErrors(errorList);
        return entityValidationWrapper;
    }

    public EntityValidationWrapper buildFromError(GeneralException ex) {
        EntityValidationWrapper entityValidationWrapper = new EntityValidationWrapper();
        entityValidationWrapper.setEntity(ex.getEntity());
        entityValidationWrapper.setMessage(ex.getMessage());
        entityValidationWrapper.setCode(ex.getCode());
        return entityValidationWrapper;
    }

    public EntityValidationWrapper buildFromError(HttpMediaTypeNotSupportedException ex) {
        EntityValidationWrapper entityValidationWrapper = new EntityValidationWrapper();
        entityValidationWrapper.setMessage(ex.getMessage());
        entityValidationWrapper.setCode(GE_04.name());
        return entityValidationWrapper;
    }

    public EntityValidationWrapper buildFromError(HttpMessageNotReadableException ex) {
        EntityValidationWrapper entityValidationWrapper = new EntityValidationWrapper();
        entityValidationWrapper.setMessage(GE_05.getMessage());
        entityValidationWrapper.setCode(GE_05.name());
        return entityValidationWrapper;
    }

}
