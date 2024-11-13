package mino;

import main.Inputs;
import main.ManagerJuego;
import mino.forms.DireccionInteface;

import java.awt.*;

public class Mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    private int autoDropCount = 0;
    private Inputs inputs;
    private int direction = 1;//Puede haber 4 direcciones 1,2,3o4.
    private boolean colisionIzquierda = false, colisionDerecha = false, colisionAbajo = false;
    private boolean active = true;
    private boolean activando;
    private int activandoContador = 0;


    public void create(Color color) {
        for (int i = 0; i < b.length; i++) {
            b[i] = new Block(color);
        }
        for (int i = 0; i < tempB.length; i++) {
            tempB[i] = new Block(color);
        }

        inputs = new Inputs();
    }

    public void setXY(int x, int y) {
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActivando() {
        return activando;
    }

    public void setActivando(boolean activando) {
        this.activando = activando;
    }

    public void activando() {
        activandoContador++;
        if (activandoContador == 45) {

            activandoContador = 0;
            colisionMovimiento();
            if (colisionAbajo) {

                active = false;
            }
        }
    }

    public void updateXY(int direccion) {
        colisionRotacion();

        if (colisionIzquierda == false && colisionDerecha == false && colisionAbajo == false) {
            this.direction = direccion;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }

    }

    ;

    public void colisionMovimiento() {
        colisionIzquierda = false;
        colisionDerecha = false;
        colisionAbajo = false;

        colisionBloquesEstaticos();

        //Pared Izquierda
        for (int i = 0; i < b.length; i++) {
            if (b[i].x == ManagerJuego.left_x) {
                colisionIzquierda = true;
            }
        }
        //Pared Derecha
        for (int i = 0; i < b.length; i++) {
            if ((b[i].x + Block.SIZE) == ManagerJuego.right_x) {
                colisionDerecha = true;
            }
        }
        //Piso
        for (int i = 0; i < b.length; i++) {
            if ((b[i].y + Block.SIZE) >= ManagerJuego.bottom_y) {
                colisionAbajo = true;
            }
        }


    }

    public void colisionRotacion() {
        colisionIzquierda = false;
        colisionDerecha = false;
        colisionAbajo = false;

        //Pared Izquierda
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x < ManagerJuego.left_x) {
                colisionIzquierda = true;

            }
        }
        //Pared Derecha
        for (int i = 0; i < b.length; i++) {
            if ((tempB[i].x + Block.SIZE) > ManagerJuego.right_x) {
                colisionDerecha = true;

            }
        }
        //Piso
        for (int i = 0; i < b.length; i++) {
            if ((tempB[i].y + Block.SIZE) > ManagerJuego.bottom_y) {
                colisionAbajo = true;

            }
        }
    }

    private void colisionBloquesEstaticos() {
        for (Block staticBlock : ManagerJuego.bloquesEstaticos) {
            int staticBlockX = staticBlock.x;
            int staticBlockY = staticBlock.y;

            for (Block movingBlock : b) {
                // Verificar colisión de abajo
                if (movingBlock.y + Block.SIZE == staticBlockY && movingBlock.x == staticBlockX) {
                    colisionAbajo = true;
                }
                // Verificar colisión izquierda
                if (movingBlock.x - Block.SIZE == staticBlockX && movingBlock.y == staticBlockY) {
                    colisionIzquierda = true;
                }
                // Verificar colisión derecha
                if (movingBlock.x + Block.SIZE == staticBlockX && movingBlock.y == staticBlockY) {
                    colisionDerecha = true;
                }

            }
        }
    }

    public void getDireccion1() {
    }

    public void getDireccion2() {
    }

    public void getDireccion3() {
    }

    public void getDireccion4() {
    }

    public void update() {
        if (activando) {
            activando();
        }
        //Controlar el Mino.
        if (inputs.isKUp()) {
            switch (direction) {
                case 1:
                    getDireccion2();
                    break;
                case 2:
                    getDireccion3();
                    break;
                case 3:
                    getDireccion4();
                    break;
                case 4:
                    getDireccion1();
                    break;
            }
            inputs.setKUp(false);
        }
        colisionMovimiento();

        if (inputs.isKDown()) {
            if (colisionAbajo == false) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                inputs.setKDown(false);
            }
        }

        if (inputs.isKLeft()) {
            if (colisionIzquierda == false) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;

                inputs.setKLeft(false);
            }
        }
        if (inputs.isKRight()) {
            if (colisionDerecha == false) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;

                inputs.setKRight(false);
            }
        }
        if (colisionAbajo) {
            activando = true;
        } else {
            autoDropCount++;
            if (autoDropCount == ManagerJuego.intervaloCaida) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCount = 0;

            }
        }
    }

    public void draw(Graphics2D g2) {
        int margen = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margen, b[0].y + margen, Block.SIZE - (margen * 2), Block.SIZE - (margen * 2));
        g2.fillRect(b[1].x + margen, b[1].y + margen, Block.SIZE - (margen * 2), Block.SIZE - (margen * 2));
        g2.fillRect(b[2].x + margen, b[2].y + margen, Block.SIZE - (margen * 2), Block.SIZE - (margen * 2));
        g2.fillRect(b[3].x + margen, b[3].y + margen, Block.SIZE - (margen * 2), Block.SIZE - (margen * 2));
    }

    public boolean isActive() {
        return active;
    }

    public boolean isColisionAbajo() {
        return colisionAbajo;
    }

    public boolean isColisionDerecha() {
        return colisionDerecha;
    }

    public boolean isColisionIzquierda() {
        return colisionIzquierda;
    }
}