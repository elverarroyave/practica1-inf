package co.edu.udea.edatos.ejemplo.exception;

public class NotFoundEntityException extends Exception {
    public NotFoundEntityException() {
        super("Not found entity in database");
    }
}
