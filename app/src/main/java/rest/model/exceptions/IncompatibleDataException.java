package rest.model.exceptions;

import java.util.UUID;

public class IncompatibleDataException extends Exception{
    public IncompatibleDataException(String msg){
        super(msg);
    }
}
