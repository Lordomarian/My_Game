import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound implements AutoCloseable {
    private boolean released ;
    private AudioInputStream stream = null;
    private Clip clip = null;
    private boolean playing = false;
    private FloatControl volumeControl = null;

    public  Sound  (String name, float x) {
        try {
            URL defaultSound = this.getClass().getResource(name);
            AudioInputStream ais = AudioSystem.getAudioInputStream(defaultSound);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(x);
            released = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            released = false;

            close();
        }

    }
    public boolean isPlaying() {
        return playing;
    }
    public boolean isReleased() {
        return released;
    }

    public void play ( boolean breakOld){
        if (released) {
            if (breakOld) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
    }
    public void close() {
        if (clip != null)
            clip.close();

        if (stream != null)
            try {
                stream.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
    }
    public void setVolume(float x) {
        if (x<0) x = 0;
        if (x>1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max-min)*x+min);
    }
}