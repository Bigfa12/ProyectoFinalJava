package Exeption;

public class NombreRepetidoExeption extends RuntimeException{
    public NombreRepetidoExeption(String nombre) {
        super("El nombre" + nombre + "ya existe");
    }
}
