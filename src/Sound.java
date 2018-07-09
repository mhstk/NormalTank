import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import javax.sound.sampled.Clip ;
import javax.sound.sampled.AudioSystem;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Sound extends SwingWorker<Object, Object> {
    private AudioPlayer MGP = AudioPlayer.player;
    private AudioStream BGM;
    private String url;
    private int sleepTime ;
    private boolean isPlaying = true;

    public Sound(String url,int sleepTime) {
        this.url = url;
        this.sleepTime = sleepTime;
    }

    @Override
    protected Object doInBackground() {

        while (isPlaying) {
        try {
            BGM = new AudioStream(new FileInputStream(new File(url)));

        } catch (IOException e) {
            e.printStackTrace();
        }
            System.out.println("jdk");
        MGP.start(BGM);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void cancel(){
        MGP.stop(BGM);
        isPlaying = false;
    }
}