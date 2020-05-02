package co.edu.udea.edatos.ejemplo.util;

public class DuplicateItemException extends RuntimeException
{
    /**
     * Construct this exception object.
     */
    public DuplicateItemException( )
    {
        super( );
    }
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public DuplicateItemException( String message )
    {
        super( message );
    }
}