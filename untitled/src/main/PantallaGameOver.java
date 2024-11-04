package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PantallaGameOver extends JPanel implements Runnable {

    Thread thread;
    Inputs inputs = new Inputs();


    @Override
    public void run() {
        System.out.println("HOLA");
        this.setPreferredSize(new Dimension(PanelJuego.ANCHO, PanelJuego.ALTO));
        this.setBackground(Color.BLACK);
        //Anulo cualquier tipo de layout por default.
        this.setLayout(null);

        //Implementar Inputs
        this.addKeyListener(new Inputs());
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}
