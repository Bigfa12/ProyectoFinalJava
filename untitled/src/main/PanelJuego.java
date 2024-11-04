package main;

import javax.swing.*;
import java.awt.*;


public class PanelJuego extends JPanel implements Runnable {

    //Seteo el ancho y alto del PanelJuego.
    public static final int ANCHO = 1280;
    public static final int ALTO = 720;
    final int FPS = 60;
    //Uso thread para poder hacer el GameLoop.
    Thread threadJuego;
    ManagerJuego mj;
    Inputs inputs = new Inputs();


    public PanelJuego() {
        //Configuracion del Panel.

        this.setPreferredSize(new Dimension(ANCHO, ALTO));
        this.setBackground(Color.BLACK);
        //Anulo cualquier tipo de layout por default.
        this.setLayout(null);
        mj = new ManagerJuego();
        //Implementar Inputs
        this.addKeyListener(new Inputs());
        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    public void lanzarJuego() {
        threadJuego = new Thread(this);
        //El metodo start() llama automaticamente al metodo run().
        threadJuego.start();
    }

    @Override
    public void run() {

        //Loop de juego.
        double dibujarIntervalo = 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;
        while (threadJuego != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / dibujarIntervalo;
            lastTime = currentTime;
            if (delta >= 1) {
                //Esto se llama 60 veces x seg.
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (inputs.isKSpace() == false && mj.isJuegoTerminado() == false) {
            mj.update();
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        mj.dibujar(g2);
    }


}
