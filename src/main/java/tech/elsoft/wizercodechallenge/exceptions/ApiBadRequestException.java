package tech.elsoft.wizercodechallenge.exceptions;

public class ApiBadRequestException extends RuntimeException{

    public ApiBadRequestException(String message) {
        super(message);
    }
}
