package pe.indigital.tunki.core.example.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldValidationWrapper {

    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private String message;

}
