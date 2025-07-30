package es.cic.curso25.proy009.Service;

public class ArbolNoEncontradoException extends RuntimeException    {

    public ArbolNoEncontradoException(Long id) {
        super("Árbol no encontrado con id: " + id);
    }

}
