package es.cic.curso25.proy009.Service;

public class RamaNoEncontradaException extends RuntimeException{

    public RamaNoEncontradaException(Long id) {
        super("Rama no encontrada con id: " + id);
    }
}
