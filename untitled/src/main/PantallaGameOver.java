package main;

import Exeption.NombreRepetidoExeption;
import Exeption.NombreVacioExeption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;

public class PantallaGameOver extends JPanel {

    private ManagerJuego managerJuego;

    private String nombre;

    private List<Jugador> jugadores;

    private int puntos;

    public PantallaGameOver() {
        this.setPreferredSize(new Dimension(PanelJuego.ANCHO, PanelJuego.ALTO));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        jugadores = new ArrayList<>();


        // Panel principal con borde y color de fondo
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Etiqueta "Game Over" centrada
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(gameOverLabel);

        // Etiqueta para ingresar el nombre
        JLabel nameLabel = new JLabel("Ingrese su nombre");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(nameLabel);

        // Campo de texto para el nombre
        JTextField nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(200, 30));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(nameField);

        // Botón "Aceptar"
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(acceptButton);

        // Etiqueta para mostrar la puntuación

        JLabel scoreLabel = new JLabel("Tu puntuacion: " + GameData.getScore());
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(scoreLabel);

        // Acción del botón "Aceptar"
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre = nameField.getText();
                Jugador j = new Jugador(nombre, GameData.getScore());
                try {
                    if (!nameField.getText().equals("")) {
                        if (!estaRepetido(j)) {

                        } else {
                            throw new NombreRepetidoExeption(nombre);
                        }

                    } else {
                        throw new NombreVacioExeption();
                    }
                } catch (NombreRepetidoExeption ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (NombreVacioExeption ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());

                }
            }
        });
    }

    public boolean estaRepetido(Jugador j) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(j.getNombre())) {
                return true;
            }
        }
        return false;
    }
}
