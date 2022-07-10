package rest.model.exception;

import java.util.UUID;

public class NotFoundException extends Exception{
    public NotFoundException(UUID id){
        super("Can't find unit with UUID: " + id.toString());
    }
}

