package main;

import org.json.JSONArray;
import org.json.JSONObject;

public class Jugador {
    private String nombre;
    private int puntos;

    public Jugador() {
    }

    public Jugador(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public JSONObject jugadorToJSON(){
        JSONObject dataJSON = new JSONObject();
        dataJSON.put("nombre", nombre);
        dataJSON.put("puntos", puntos);
        return dataJSON;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "[nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                "]--";
    }





}
