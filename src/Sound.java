import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Sound extends SwingWorker<Object, Object> {

    private boolean playCanceled;
    private String url;
    private Clip audioClip;
    private int sleepTime;

    public Sound(String url, int sleepTime) {
        this.url = url;
        this.sleepTime = sleepTime;
    }

    @Override
    protected Object doInBackground() {
        File audioFile = new File(url);
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                AudioFormat format = audioStream.getFormat();

                DataLine.Info info = new DataLine.Info(Clip.class, format);

                audioClip = (Clip) AudioSystem.getLine(info);

                audioClip.open(audioStream);

                audioClip.start();
                audioClip.loop(sleepTime);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }

        return null;
    }

    public void cancel(){
        audioClip.close();
    }
}
