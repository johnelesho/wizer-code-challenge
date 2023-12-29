package tech.elsoft.wizercodechallenge.exceptions;

public class ApiNotFoundException extends RuntimeException{


    public ApiNotFoundException(String message) {
        super(message);
    }
}
