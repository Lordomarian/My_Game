import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound implements AutoCloseable {
    private boolean released ;
    private AudioInputStream stream = null;
    private static Clip clip = null;
    private static Clip clip1 = null;
    private boolean playing = false;
    private static FloatControl volumeControl = null;

    public Sound (String name, float x){
        try {
            URL defaultSound = this.getClass().getResource(name);
            AudioInputStream ais = AudioSystem.getAudioInputStream(defaultSound);
            clip = AudioSystem.getClip();
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
    public static void repeat (String name, float x, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        URL defaultSound = Sound.class.getResource(name);
        AudioInputStream ais = AudioSystem.getAudioInputStream(defaultSound);
        clip1 = AudioSystem.getClip();
        clip1.open(ais);
        clip1.setLoopPoints(0,-1);
        clip1.loop(count);
        volumeControl = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(x);
    }
    public static void setVolume1(float x){
        volumeControl = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(x);
    }
    public static void setVolume(float x) {

        if (x<0) x = 0;
        if (x>1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max-min)*x+min);
    }
}