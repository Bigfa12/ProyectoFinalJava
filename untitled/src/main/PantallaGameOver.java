package main;

import Exeption.NombreRepetidoExeption;
import Exeption.NombreTerminadoExeption;
import Exeption.NombreVacioExeption;
import org.json.JSONArray;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PantallaGameOver extends JPanel {

    private ManagerJuego managerJuego;
    private String nombre;
    private ArrayList<Jugador> jugadores;
    private int puntos;
    private boolean nombreTerminado;

    public PantallaGameOver(ManagerJuego mj) {
        this.setPreferredSize(new Dimension(PanelJuego.ANCHO, PanelJuego.ALTO));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        managerJuego = mj;
        jugadores = managerJuego.getJugadores();

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
        nameField.setEnabled(true);
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
                Jugador jugador = new Jugador(nombre, GameData.getScore());

                try {
                    if (nombreTerminado == true){
                        throw new NombreTerminadoExeption();
                    }
                    if (nombre.isEmpty()) {
                        throw new NombreVacioExeption();
                    }
                     if (!estaRepetido(jugador)) {
                         jugadores.add(jugador);
                         clasificarJugadores(jugadores,jugador);

                    }else {
                         throw new NombreRepetidoExeption(nombre);
                     }

                } catch (NombreRepetidoExeption | NombreVacioExeption ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }catch (NombreTerminadoExeption ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
    }

    public boolean estaRepetido(Jugador jugador) {
        for (Jugador j : jugadores) {
            if (j.getNombre().equals(jugador.getNombre())) {
                return true;
            }
        }
        return false;
    }

    // Convierte todos los jugadores a un JSONArray
    public JSONArray toJSONArray() {
        JSONArray jsonArray = new JSONArray();
        for (Jugador jugador : jugadores) {
            jsonArray.put(jugador.jugadorToJSON());
        }
        System.out.println(jsonArray.toString());
        return jsonArray;
    }
    public void clasificarJugadores(List<Jugador> jugadores,Jugador jugador){
        if (!jugadores.isEmpty()) {
            jugadores.sort(Comparator.comparingInt(Jugador::getPuntos).reversed());


            // Clasificar los puntajes en TOPSCORE, REGULARSCORE, LOWSCORE
            for (int i = 0; i < jugadores.size(); i++) {
                if (i < 3) {
                    jugadores.get(i).setScores(Scores.TOPSCORE);
                } else if (i < 6) {
                    jugadores.get(i).setScores(Scores.REGULARSCORE);
                } else {
                    jugadores.get(i).setScores(Scores.LOWSCORE);
                }
            }
            JSONUtiles.uploadJSON(toJSONArray(), "tetrisData");
        }

    }
}
