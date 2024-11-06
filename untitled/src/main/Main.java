package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::crearVentanaPrincipal);
    }

    private static void crearVentanaPrincipal() {
        // Crear ventana
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        JPanel mainPanel = new JPanel(new CardLayout());

        // Agregar el PanelJuego
        PanelJuego panelJuego = new PanelJuego(mainPanel);
        PantallaGameOver pantallaGameOver = new PantallaGameOver();
         mainPanel.add(panelJuego, "Juego");
        mainPanel.add(pantallaGameOver, "GameOver");

        window.add(mainPanel);
        // Ajustar el tamaño de la ventana al contenido
        window.pack();

        // Inicializar el juego
        try {
            panelJuego.lanzarJuego();
        } catch (Exception e) {
            System.err.println("Error al lanzar el juego: " + e.getMessage());
        }

        // Centrar la ventana en la pantalla
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
