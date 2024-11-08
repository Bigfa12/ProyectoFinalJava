package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Musica<T extends Clip> {
    private Map<String, T> audioClips;

    public Musica() {
        audioClips = new HashMap<>();
    }

    // Método para cargar un archivo de audio y almacenarlo en el mapa
    public void cargarAudio(String name, String rutaArchivo) {
        try {
            File archivoSonido = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
            T clip = (T) AudioSystem.getClip();
            clip.open(audioStream);
            audioClips.put(name, clip);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Formato de archivo no soportado: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de audio: " + rutaArchivo);
        } catch (LineUnavailableException e) {
            System.out.println("Línea de audio no disponible.");
        }
    }

    // Método para reproducir el audio
    public void play(String name) {
        T clip = audioClips.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Detener el clip si ya está en ejecución
            }
            clip.setFramePosition(0); // Reiniciar el audio al inicio
            clip.start();
        } else {
            System.out.println("El audio '" + name + "' no está cargado.");
        }
    }

    // Método para detener el audio
    public void stop(String name) {
        T clip = audioClips.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Método para reproducir el audio en bucle
    public void loop(String name) {
        T clip = audioClips.get(name);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Método para ajustar el volumen
    public void setVolume(String name, float volume) {
        T clip = audioClips.get(name);
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            float nuevoVolumen = Math.min(Math.max(volume, volumeControl.getMinimum()), volumeControl.getMaximum());
            volumeControl.setValue(nuevoVolumen);
        }
    }
}