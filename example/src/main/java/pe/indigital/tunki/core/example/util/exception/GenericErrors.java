package pe.indigital.tunki.core.example.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenericErrors implements Errors {

    GE_00("General Error Exception"),
    GE_01("Data is not valid"),
    GE_02("Entity not found with id %s"),
    GE_03("None entity was found for query"),
    GE_04("Unsupported Media Type"),
    GE_05("Bad Body Request");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

}
