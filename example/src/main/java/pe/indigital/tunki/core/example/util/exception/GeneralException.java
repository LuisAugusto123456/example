package pe.indigital.tunki.core.example.util.exception;

public abstract class GeneralException extends Exception {

    public GeneralException(String msg) {
        super(msg);
    }

    public abstract String getCode();

    @Override
    public abstract String getMessage();

    public abstract String getEntity();

}
