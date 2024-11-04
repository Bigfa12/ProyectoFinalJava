package main;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Musica{
    private Clip clip;

    public Musica(String ruta) {

       try {
           File file = new File(ruta);
           AudioInputStream ais = AudioSystem.getAudioInputStream(file);
           clip = AudioSystem.getClip();
           clip.open(ais);

       }catch (UnsupportedAudioFileException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }catch (LineUnavailableException e){
           e.printStackTrace();
       }

    }
    public void play(){
        if (clip != null){
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop(){
        if (clip != null && clip.isRunning()){
            clip.stop();
            clip.close();
        }
    }
    public void playUNAVEZ(){
        if (clip != null){
            clip.start();
        }
    }

}
