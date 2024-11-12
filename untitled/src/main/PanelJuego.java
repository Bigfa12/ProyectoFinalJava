package main;

import javax.swing.*;
import java.awt.*;

public class PanelJuego extends JPanel implements Runnable {

    public static final int ANCHO = 1280;
    public static final int ALTO = 720;
    private static final int FPS = 60;

    private final JPanel mainPanel;
    private final ManagerJuego mj;
    private final Inputs inputs = new Inputs();
    private Thread threadJuego;
    private int contador = 0;

    public PanelJuego(JPanel mainPanel) {
        this.mainPanel = mainPanel;

        this.setPreferredSize(new Dimension(ANCHO, ALTO));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        mj = new ManagerJuego();

        this.addKeyListener(inputs);
        this.setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);


    }

    public void lanzarJuego() {
        threadJuego = new Thread(this);
        threadJuego.start();
    }

    @Override
    public void run() {
        double dibujarIntervalo = 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;

        while (threadJuego != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / dibujarIntervalo;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!inputs.isKSpace() && !mj.isJuegoTerminado()) {
            mj.update();
        } else if (mj.isJuegoTerminado()) {
            cambiarAGameOver();

        }
    }

    private void cambiarAGameOver() {
        if (contador > 200 || inputs.isKSpace()) {
            PantallaGameOver pantallaGameOver = new PantallaGameOver(mj);
            mainPanel.add(pantallaGameOver, "GameOver");
            CardLayout cl = (CardLayout) (mainPanel.getLayout());
            cl.show(mainPanel, "GameOver");
            threadJuego = null;// tuve que hacerlo null porque si no nunca dejaba escribir el nombre en el game over
            contador = 0;
        } else {
            contador++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        mj.dibujar(g2);
    }
}
