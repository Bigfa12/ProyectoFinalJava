package main;
/*Esta clase va a : -Dibujar el area de juego.
                    -Manejar las tetrisFiguras.
                    -Manejar las acciones del juego.
 */

import mino.Block;
import mino.Mino;
import mino.forms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerJuego {
    final int ANCHO = 360;
    final int ALTO = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Mino
    Mino minoActual;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino minoSiguiente;
    final int SIGUINTE_START_X;
    final int SIGUINTE_START_Y;
    public static List<Block> bloquesEstaticos = new ArrayList<>();

    private Inputs inputs;
    //Otros
    public static int intervaloCaida = 60;
    private boolean juegoTerminado = false;
    //Score
    private int score = 0;
    private int lineasEliminadas = 0;
    private int level = 1;
    private Musica musica;
    private Musica eliminarLinea;
    private Musica pasarNivel;

    public ManagerJuego() {
        inputs = new Inputs();

        left_x = (PanelJuego.ANCHO / 2) - (ANCHO / 2);
        right_x = left_x + ANCHO;
        top_y = 50;
        bottom_y = top_y + ALTO;

        MINO_START_X = left_x + (ANCHO / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        SIGUINTE_START_X = right_x + 175;
        SIGUINTE_START_Y = top_y + 500;
        //Iniciar el Mino
        minoActual = randomMino();
        minoActual.setXY(MINO_START_X, MINO_START_Y);
        minoSiguiente = randomMino();
        minoSiguiente.setXY(SIGUINTE_START_X, SIGUINTE_START_Y);
        musica = new Musica("C:\\UTNprogramacion\\EXTRA\\Games\\Tetris\\untitled\\src\\sonidos\\Cancion del Tetris (Original Song) con Teclado..wav");
        eliminarLinea = new Musica("src/sonidos/eliminarLinea.wav");
        pasarNivel = new Musica("src/sonidos/pasarNivel.wav");
    }

    private Mino randomMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                mino = new Mino_L1();
                break;
            case 1:
                mino = new Mino_L2();
                break;
            case 2:
                mino = new Mino_Bar();
                break;
            case 3:
                mino = new Mino_Square();
                break;
            case 4:
                mino = new Mino_T();
                break;
            case 5:
                mino = new Mino_Z1();
                break;
            case 6:
                mino = new Mino_Z2();
                break;
        }
        return mino;
    }

    public void update() {
        //Verificar si el Mino esta activo.
        musica.play();
        if (minoActual.isActive() == false) {
            //Si el mino no esta activo lo pongo en la lista de bloques estaticos.
            bloquesEstaticos.add(minoActual.b[0]);
            bloquesEstaticos.add(minoActual.b[1]);
            bloquesEstaticos.add(minoActual.b[2]);
            bloquesEstaticos.add(minoActual.b[3]);

            if (minoActual.b[0].x == MINO_START_X && minoActual.b[0].y == MINO_START_Y) {
                juegoTerminado = true;
            }

            minoActual.setActive(false);

            minoActual = minoSiguiente;
            minoActual.setXY(MINO_START_X, MINO_START_Y);
            minoSiguiente = randomMino();
            minoSiguiente.setXY(SIGUINTE_START_X, SIGUINTE_START_Y);

            verificarLinea();
        } else {
            minoActual.update();
        }

    }

    public void dibujar(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, ANCHO + 8, ALTO + 8);

        int x = right_x + 100;
        int y = bottom_y - 200;

        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SIGUIENTE", x + 23, y + 40);

        //Rectangulo de Score
        g2.drawRect(x, top_y, 240, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("Nivel: " + level, x, y);
        y += 70;
        g2.drawString("Lineas: " + lineasEliminadas, x, y);
        y += 70;
        g2.drawString("Puntos: " + score, x, y);


        //Dibujar el Mino.
        if (minoActual != null) {
            minoActual.draw(g2);
        }
        minoSiguiente.draw(g2);

        //Dibujar Bloques Estaticos.
        for (int i = 0; i < bloquesEstaticos.size(); i++) {
            bloquesEstaticos.get(i).dibujar(g2);
        }


        //Dibujar PAUSA
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (juegoTerminado) {
            x = left_x + 25;
            y = top_y + 320;

            bloquesEstaticos.removeAll(bloquesEstaticos);

            g2.drawString("GAME OVER", x, y);
        } else if (inputs.isKSpace()) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("Pausa", x+35, y);
        }
    }

    //verificamos si la linea esta completa, si lo esta, elimina toda la linea y caen los bloques de arriba.
    public void verificarLinea() {
        int x = left_x;
        int y = top_y;
        int cuentaBloques = 0;
        int cuentaLineas = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < bloquesEstaticos.size(); i++) {
                if (bloquesEstaticos.get(i).x == x && bloquesEstaticos.get(i).y == y) {
                    cuentaBloques++;
                }
            }
            x += Block.SIZE;

            if (x == right_x) {
                if (cuentaBloques == 12) {
                    for (int i = bloquesEstaticos.size() - 1; i > -1; i--) {
                        if (bloquesEstaticos.get(i).y == y) {
                            bloquesEstaticos.remove(i);
                        }
                    }
                    lineasEliminadas++;
                    eliminarLinea.playUNAVEZ();


                    cuentaLineas++;

                    if (lineasEliminadas % 10 == 0 && intervaloCaida > 1) {
                        level++;
                        if (intervaloCaida > 10){
                            intervaloCaida += 10;
                        }else{
                            intervaloCaida-=1;
                        }
                    }


                    for (int j = 0; j < bloquesEstaticos.size(); j++) {
                        if (bloquesEstaticos.get(j).y < y) {
                            bloquesEstaticos.get(j).y += Block.SIZE;


                        }
                    }
                }
                cuentaBloques = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        if (cuentaLineas > 0){
            int scoreLinea = 10 * level;
            score += scoreLinea * cuentaLineas;
        }

    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
