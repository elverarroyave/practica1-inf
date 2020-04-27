package co.edu.udea.edatos.ejemplo.exception;

public class DuplicateKeyException extends Exception {
    public DuplicateKeyException() {
        super("The id is already register");
    }
}
