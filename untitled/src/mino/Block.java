package mino;

import java.awt.*;

public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;//Bloque de 30x30.
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void dibujar(Graphics2D g2) {
        int margen = 2;
        g2.setColor(c);
        g2.fillRect(x + margen, y + margen, SIZE - (margen * 2), SIZE - (margen * 2));
    }

}
