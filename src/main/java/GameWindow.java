import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class GameWindow extends JFrame {
    private static long last_frame_time;
    private static GameWindow game_window;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static float drop_left = 400;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static float drop_vy = 200;
    private static int score = 0;
    private static int live = 3;
    public static void main(String[] args) throws IOException {
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("BackGround.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        game_window.setTitle("Score = " + score + " live = " + live);
        GameField game_Field = new GameField();
        game_Field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean is_drop = x >= drop_left && x <= drop_right && y <= drop_bottom && y >= drop_top;
                if (is_drop) {
                    Random rand = new Random();
                    int r = rand.nextInt(20) - 10;
                    Sound s = new Sound("zvuk-kapli.wav",0.4f);
                    drop_top = -100;
                    drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                    drop_v = drop_v + 20;
                    drop_vy = 20 * r;
                    score += 1;
                    game_window.setTitle("Score = " + score + " live = " + live);
                }
            }
        });
        game_window.add(game_Field);
        game_window.setVisible(true);
    }
    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float drop_right = drop_left + drop.getWidth(null);
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        boolean is_drop1 = 0 > drop_left || drop_right > 906;
        if (is_drop1) {
            drop_vy = drop_vy * -1;
        }
        drop_top = drop_top + drop_v * delta_time;
        drop_left = drop_left + drop_vy * delta_time;
        g.drawImage(backGround, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (live <= 0) {
            g.drawImage(gameOver, 280, 120, null);
            drop_v = 0;
            drop_vy = 0;
            drop_top = -100;
        }
        if (drop_top > game_window.getHeight()) {
            live = live - 1;
            Sound s = new Sound("fail.wav",0.55f);
            Random rand = new Random();
            int r = rand.nextInt(20) - 10;
            drop_top = -100;
            drop_left = (int) (Math.random() * ((game_window.getWidth()) - drop.getWidth(null)));
            drop_v = 200;
            drop_vy = 20 * r;
            game_window.setTitle("Score = " + score + " live = " + live);
            if (live == 0) {
                Sound t = new Sound("death.wav",0.65f);
            }
        }
        game_window.setTitle("Score = " + score + " live = " + live);
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();

        }

    }

    public static class Sound implements AutoCloseable {
        private boolean released = false;
        private AudioInputStream stream = null;
        private Clip clip = null;
        private boolean playing = false;
        private FloatControl volumeControl = null;

        public  Sound  (String name, float x) {
            try {
               // File soundFile = new File(name);
                URL defaultSound = this.getClass().getResource(name);
                AudioInputStream ais = AudioSystem.getAudioInputStream(defaultSound);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(x);
                released = true;

            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
                live = 1000;
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
}
