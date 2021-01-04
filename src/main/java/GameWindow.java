
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
      /* @author Mihail Kolzlov
       *create by Mihail Kozlov 30.12.2020
       * My game catch a drop 
       */

public class GameWindow extends JFrame {
    private static boolean i = true;
    private static long last_frame_time;
    private static GameWindow game_window;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static Image start_Menu;
    private static float drop_left = 400;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static float drop_vy = 200;
    private static int score = 0;
    private static int live = 3;
    public static void main(String[] args) throws IOException {
        new SwingApp();
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
        g.drawImage(backGround, -253, 0, null);
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
//     // interface Menu /*extends JPanel*/ {
////        @Override
////        protected void paintComponent (Graphics g) {
////            super.paintComponent(g);
////            g.drawImage(backGround, 0, 0, null);
////            g.drawImage(start_Menu, 300, 150, null);
////        }
//            // default float Menu(float smr, float smb) throws IOException {
////            m.addMouseListener(new MouseAdapter() {
////                @Override
////                public void mousePressed(MouseEvent e) {
////                    super.mousePressed(e);
////                    int x = e.getX();
////                    int y = e.getY();
//                    smr = 300 + start_Menu.getWidth(null);
//                    smb = 150 + start_Menu.getHeight(null);
////                    boolean is_menu = x >= 300 && x <= smr && y <= smb && y >= 150;
////                    if (is_menu) {
////                        i = false;
////                        //GameField game_Field = new GameField();
////                       // game_Field.start(game_Field);
////                        //game_window.add(game_Field);
////                        game_window.setTitle("fail 2 ");
////
//                   return smb;
//             }
              //  }
            //});

    public static class TestActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            GameField gameField = new GameField();
            try {
                GameField.start(gameField);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            game_window.setTitle("asdsada");
        }
    }
     // }
     public static class SwingApp extends JFrame  {

        public void createAndShowGUI(Container panel){
             panel.setLayout(null);
             panel.setBackground(new Color( 53,53,73));

             JButton quitButton = new JButton("Exit");
             JButton scoreButton = new JButton("Score");
             JButton startButton = new JButton("Start");
             JButton settingButton = new JButton("Settings");
             JButton creditButton = new JButton("Credit");

             quitButton.addActionListener(e -> System.exit(0));
             setSize(305, 340);
             quitButton.setPreferredSize(new Dimension(150,100));
             setPreferredSize(new Dimension(100,90));
             Insets insets = panel.getInsets();
             Dimension size = getPreferredSize();
             startButton.setBounds( 25 +insets.left, 20 + insets.top,size.width,size.height);
             scoreButton.setBounds( 175 + insets.left , 20 + insets.top,size.width,size.height);
             settingButton.setBounds(25 + insets.left, 135 + insets.top,size.width,size.height);
             creditButton.setBounds(175 + insets.left, 135 + insets.top,size.width,size.height);
             quitButton.setBounds(25 + insets.left, 250 + insets.top,size.width + 150,size.height -50);
             panel.add(startButton);
             panel.add(scoreButton);
             panel.add(quitButton);
             panel.add(settingButton);
             panel.add(creditButton);
         }
         public SwingApp() throws IOException {
             JPanel panel = new JPanel();
             panel.setLayout(null);
             panel.setBackground(new Color( 53,53,73));

             JButton quitButton = new JButton("Exit");
             JButton scoreButton = new JButton("Score");
             JButton startButton = new JButton("Start");
             JButton settingButton = new JButton("Settings");
             JButton creditButton = new JButton("Credit");

             quitButton.addActionListener(e -> System.exit(0));
             setSize(305, 340);
             quitButton.setPreferredSize(new Dimension(150,100));
             setPreferredSize(new Dimension(100,90));
             Insets insets = panel.getInsets();
             Dimension size = getPreferredSize();
             startButton.setBounds( 25 +insets.left, 20 + insets.top,size.width,size.height);
             scoreButton.setBounds( 175 + insets.left , 20 + insets.top,size.width,size.height);
             settingButton.setBounds(25 + insets.left, 135 + insets.top,size.width,size.height);
             creditButton.setBounds(175 + insets.left, 135 + insets.top,size.width,size.height);
             quitButton.setBounds(25 + insets.left, 250 + insets.top,size.width + 150,size.height -50);
             panel.add(startButton);
             panel.add(scoreButton);
             panel.add(quitButton);
             panel.add(settingButton);
             panel.add(creditButton);

//             pane.setLayout(null);
//
//             JButton b1 = new JButton("one");
//             JButton b2 = new JButton("two");
//             JButton b3 = new JButton("three");
//
//             pane.add(b1);
//             pane.add(b2);
//             pane.add(b3);
//
//             Insets insets = pane.getInsets();
//             Dimension size = b1.getPreferredSize();
//             b1.setBounds(25 + insets.left, 5 + insets.top,
//                     size.width, size.height);
//             size = b2.getPreferredSize();
//             b2.setBounds(55 + insets.left, 40 + insets.top,
//                     size.width, size.height);
//             size = b3.getPreferredSize();
//             b3.setBounds(150 + insets.left, 15 + insets.top,
//                     size.width + 50, size.height + 20);
//
//...//In the main method:
//             Insets insets = frame.getInsets();
//             frame.setSize(300 + insets.left + insets.right,
//                     125 + insets.top + insets.bottom);

             ActionListener st  = new TestActionListener();
             startButton.addActionListener(st);
             setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

             getContentPane().add(panel);
             //panel.setSize(600,600);
             //panel.setLocation(500,500);
             setResizable(false);
             setPreferredSize(new Dimension(320, 100));
             setLocationRelativeTo(null);
             setVisible(true);
         }

     }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//            while (i) {
//                g.drawImage(backGround, 0, 0, null);
//                g.drawImage(start_Menu, 300, 150, null);
               //repaint(10);
//            }
            onRepaint(g);
          repaint();
        }

        protected static void start(GameField game_Field) throws IOException {
            backGround = ImageIO.read(GameWindow.class.getResourceAsStream("BackGround.png"));
            gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
            drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
            game_window = new GameWindow();
            game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            game_window.setLocation(500, 100);
            game_window.setSize(906, 478);
            game_window.setResizable(false);
            last_frame_time = System.nanoTime();
            game_window.setTitle("Score = " + score + " live = " + live);
            game_window.add(game_Field);
            game_window.setVisible(true);
            //game_window.setTitle("fail 10");
            game_Field.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    int x = e.getX();
                    int y = e.getY();
                    float smr = 300 + start_Menu.getWidth(null);
                    float smb = 150 + start_Menu.getHeight(null);
                    boolean is_menu = x >= 300 && x <= smr && y <= smb && y >= 150;
                    if (is_menu) {
                        i = false;
                        //GameField game_Field = new GameField();
                        // game_Field.start(game_Field);
                        //game_window.add(game_Field);
                        game_window.setTitle("fail 2 ");
                    }
                    float drop_right = drop_left + drop.getWidth(null);
                    float drop_bottom = drop_top + drop.getHeight(null);
                    boolean is_drop = x >= drop_left && x <= drop_right && y <= drop_bottom && y >= drop_top;
                    if (is_drop) {
                        Random rand = new Random();
                        int r = rand.nextInt(20) - 10;
                        Sound s = new Sound("zvuk-kapli.wav", 0.4f);
                        drop_top = -100;
                        drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                        drop_v = drop_v + 20;
                        drop_vy = 20 * r;
                        score += 1;
                        game_window.setTitle("Score = " + score + " live = " + live);
                    }
                }
            });
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
}
