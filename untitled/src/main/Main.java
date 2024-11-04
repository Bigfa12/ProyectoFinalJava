package main;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Crear ventana.
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);

        //Agrego el PanelJuego a la ventana.
        PanelJuego pj = new PanelJuego();
        PantallaGameOver pg = new PantallaGameOver();
        window.add(pj,"Juego");
        window.add(pg,"Game Over");


        //Pack hace que el PanelJuego sea el tamaño de la ventana.
        window.pack();

        pj.lanzarJuego();


        //Al tener null, Windows no busca un lugar en especifico donde abrir la ventana,
        //sino que la abre en el medio.
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}