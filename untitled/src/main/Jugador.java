package main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;


public class Jugador {
    private String nombre;
    private int puntos;
    private Scores scores;

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

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "[nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                "]--";
    }







}
