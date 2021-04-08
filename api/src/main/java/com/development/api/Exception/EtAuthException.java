package com.development.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EtAuthException extends RuntimeException{

    public EtAuthException(String message)
    {
        super(message);

    }
}
