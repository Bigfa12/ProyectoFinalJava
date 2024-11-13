package Exeption;

public class NombreTerminadoExeption extends RuntimeException {
    public NombreTerminadoExeption() {
        super("Ya has ingresado un nombre, no se puede asignar otro");
    }
}
