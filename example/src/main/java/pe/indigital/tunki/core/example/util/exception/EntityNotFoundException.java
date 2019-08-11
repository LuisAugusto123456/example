package pe.indigital.tunki.core.example.util.exception;

import static pe.indigital.tunki.core.example.util.exception.GenericErrors.GE_02;

public class EntityNotFoundException extends GeneralException {

    private final String message;

    private final String entity;

    private EntityNotFoundException(String entity, String entityId) {
        super(entityId);
        this.message = String.format(GE_02.getMessage(), entityId);
        this.entity = entity;
    }

    public EntityNotFoundException(Class entity, String entityId) {
        this(entity.getSimpleName().toLowerCase(), entityId);
    }

    @Override
    public String getCode() {
        return GE_02.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getEntity() {
        return entity;
    }

}
