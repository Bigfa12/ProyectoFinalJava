package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs implements KeyListener {


    private static boolean KUp, KDown, KLeft, KRight, KSpace;

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            KUp = true;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            KDown = true;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            KLeft = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            KRight = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            if (KSpace) {
                KSpace = false;
            } else {
                KSpace = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                KUp = false;
                break;
            case KeyEvent.VK_DOWN:
                KDown = false;
                break;
            case KeyEvent.VK_LEFT:
                KLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                KRight = false;
                break;
        }
    }

    public boolean isKUp() {
        return KUp;
    }

    public boolean isKDown() {
        return KDown;
    }

    public boolean isKLeft() {
        return KLeft;
    }

    public boolean isKRight() {
        return KRight;
    }

    public void setKUp(boolean KUp) {
        this.KUp = KUp;
    }

    public void setKDown(boolean KDown) {
        this.KDown = KDown;
    }

    public void setKLeft(boolean KLeft) {
        this.KLeft = KLeft;
    }

    public void setKRight(boolean KRight) {
        this.KRight = KRight;
    }

    public boolean isKSpace() {
        return KSpace;
    }
}
