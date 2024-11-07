package Exeption;

public class NombreVacioExeption extends RuntimeException {
    public NombreVacioExeption() {
        super("El nombre no puede estar vacio");
    }
}
