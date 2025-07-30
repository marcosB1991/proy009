package es.cic.curso25.proy009.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.cic.curso25.proy009.Service.ArbolNoEncontradoException;
import es.cic.curso25.proy009.Service.RamaNoEncontradaException;


@RestControllerAdvice
public class MiControllerAdvice { 

    private static final Logger LOGGER = LoggerFactory.getLogger(MiControllerAdvice.class);

    @ExceptionHandler(ArbolNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleArbolNoEncontrado(ArbolNoEncontradoException ex) {
        LOGGER.warn("{}", ex.getMessage());
        ex.printStackTrace();
        return ex.getMessage();
    }

    @ExceptionHandler(RamaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRamaNoEncontrada(RamaNoEncontradaException ex) {
        LOGGER.warn("{}", ex.getMessage());
        return ex.getMessage();
    }
    

}
