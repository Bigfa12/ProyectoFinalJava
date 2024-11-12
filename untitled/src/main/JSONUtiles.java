package main;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class JSONUtiles {
    public static void uploadJSON(JSONArray jsonArray, String archive){
        try{
            BufferedWriter salida = new BufferedWriter(new FileWriter(archive+".json"));
            salida.write(jsonArray.toString());
            salida.flush();
            salida.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void uploadJSON(JSONObject jsonObject, String archive){
        try{
            BufferedWriter salida = new BufferedWriter(new FileWriter(archive + ".json"));
            salida.write(jsonObject.toString(4));
            salida.flush();
            salida.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static String downloadJSON(String archive){
        StringBuilder contenido = new StringBuilder();
        String lectura= "";
        try
        {
            BufferedReader entrada = new BufferedReader(new FileReader(archive+".json"));
            while((lectura = entrada.readLine())!=null){
                contenido.append(lectura);
            }
            entrada.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }


        return contenido.toString();
    }

    public static ArrayList<Jugador> jugadorFromJSON(String nombreArchivo){
        JSONArray jsonArray = new JSONArray(downloadJSON(nombreArchivo));
        ArrayList<Jugador> jugadores= new ArrayList<Jugador>();

            for (int i = 0; i < jsonArray.length(); i++) {
                Jugador jugador1 = new Jugador();
                JSONObject jugador = jsonArray.getJSONObject(i);
                String nombre = jugador.getString("nombre");
                int puntos = jugador.getInt("puntos");
                jugador1.setNombre(nombre);
                jugador1.setPuntos(puntos);
                jugadores.add(jugador1);
            }

        return jugadores;
    }



}
