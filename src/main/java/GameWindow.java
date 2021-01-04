
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
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
    private static JFrame  panel1 = new JFrame();
    private static float drop_left = 400;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static float drop_vy = 200;
    private static int score = 0;
    private static boolean isActive = false;
    private static int live = 3;
    public static void change(){
        drop_left = 400;
        drop_top = -100;
        drop_v = 200;
        drop_vy = 200;
        score = 0;
        live = 3;
    }
    public static void main(String[] args) throws IOException {
       // SwingApp sw = new SwingApp();
        SwingApp.switchMenu();
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
                if(isActive){
                }else {
                    change();
                    GameField.start(gameField);


                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            game_window.setTitle("asdsada");
        }
    }
     // }
     public static class SwingApp extends JFrame {
         private static String menuType = "A";

         protected static void addComponentsToPanel(Container panel) {
             panel.setLayout(null);

             JButton quitButton = new JButton("Exit");
             JButton scoreButton = new JButton("Score");
             JButton startButton = new JButton("Start");
             JButton settingButton = new JButton("Settings");
             JButton creditButton = new JButton("Credit");
             ActionListener st = new TestActionListener();
             startButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     GameField gameField = new GameField();
                     try {
                         if (isActive) {
                         } else {
                             panel1.setVisible(false);
                             change();
                             GameField.start(gameField);


                         }
                     } catch (IOException ioException) {
                         ioException.printStackTrace();
                     }
                 }
             });
             quitButton.addActionListener(e -> System.exit(0));
             scoreButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     menuType = "B";
                     try {
                         switchMenu();
                     } catch (IOException ioException) {
                         ioException.printStackTrace();
                     }
                 }
             });
             creditButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     menuType = "D";
                     try {
                         switchMenu();
                     } catch (IOException ioException) {
                         ioException.printStackTrace();
                     }
                 }
             });
             settingButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     menuType = "C";
                     try {
                         switchMenu();
                     } catch (IOException ioException) {
                         ioException.printStackTrace();
                     }
                 }
             });
             startButton.setPreferredSize(new Dimension(100, 90));
             Insets insets = panel.getInsets();
             Dimension size = startButton.getPreferredSize();

             startButton.setBounds(25 + insets.left, 20 + insets.top, size.width, size.height);
             scoreButton.setBounds(175 + insets.left, 20 + insets.top, size.width, size.height);
             settingButton.setBounds(25 + insets.left, 135 + insets.top, size.width, size.height);
             creditButton.setBounds(175 + insets.left, 135 + insets.top, size.width, size.height);
             quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);
             panel.add(startButton);
             panel.add(scoreButton);
             panel.add(quitButton);
             panel.add(settingButton);
             panel.add(creditButton);
             panel.setBackground(new Color(53, 53, 73));

         }

         protected static void createAndShowGUI() {


             panel1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             panel1.setResizable(false);
             panel1.setVisible(true);
             panel1.setLocation(800, 300);
             //panel.setLocationRelativeTo();
             addComponentsToPanel(panel1.getContentPane());
             panel1.setSize(305, 340);

         }

         protected static void switchMenu() throws IOException {
             switch (menuType) {
                 case "A"://menu
                     createAndShowGUI();
                     break;
                 case "B"://score
                     panel1.getContentPane().removeAll();
                     panel1.revalidate();
                     panel1.repaint();
                     showMenuB(panel1.getContentPane());
                     break;
                 case "C"://settings
                     panel1.getContentPane().removeAll();
                     panel1.revalidate();
                     panel1.repaint();
                     showMenuC(panel1.getContentPane());
                     break;
                 case "D"://credit
                     panel1.getContentPane().removeAll();
                     panel1.revalidate();
                     panel1.repaint();
                     showMenuD(panel1.getContentPane());

                     break;

             }
         }

         private static void showMenuB(Container panel) {
             panel.setLayout(null);

             JButton quitButton = new JButton("Back");
             JButton scoreButton = new JButton("Score");
             scoreButton.setPreferredSize(new Dimension(100, 90));
             Insets insets = panel.getInsets();
             Dimension size = scoreButton.getPreferredSize();
             quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);
             panel.add(scoreButton);
             panel.add(quitButton);
             ActionListener quit = new QuitActionListener();
             quitButton.addActionListener(quit);
//             quitButton.addActionListener(new ActionListener() {
//                 @Override
//                 public void actionPerformed(ActionEvent e) {
//                     menuType = "A";
//                     try {
//                         panel1.getContentPane().removeAll();
//                         panel1.revalidate();
//                         panel1.repaint();
//                         switchMenu();
//                     } catch (IOException ioException) {
//                         ioException.printStackTrace();
//                     }
//                 }
//             });
         }


         private static void showMenuC(Container panel) {
             panel.setLayout(null);

             JButton quitButton = new JButton("Back");
             JButton scoreButton = new JButton("Score");
             scoreButton.setPreferredSize(new Dimension(100, 90));
             Insets insets = panel.getInsets();
             Dimension size = scoreButton.getPreferredSize();
             quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);
             panel.add(scoreButton);
             panel.add(quitButton);
             ActionListener quit = new QuitActionListener();
             quitButton.addActionListener(quit);
         }

         private static void showMenuD(Container panel) {
             panel.setLayout(null);

             JButton quitButton = new JButton("Back");
             JButton scoreButton = new JButton("Score");
             scoreButton.setPreferredSize(new Dimension(100, 90));
             Insets insets = panel.getInsets();
             Dimension size = scoreButton.getPreferredSize();
             quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);
             panel.add(scoreButton);
             panel.add(quitButton);
             ActionListener quit = new QuitActionListener();
             quitButton.addActionListener(quit);

         }
         public static class QuitActionListener implements ActionListener{

             @Override
             public void actionPerformed(ActionEvent e) {
                 menuType = "A";
                 try {
                     panel1.getContentPane().removeAll();
                     panel1.revalidate();
                     panel1.repaint();
                     switchMenu();
                 } catch (IOException ioException) {
                     ioException.printStackTrace();
                 }
             }
         }
     }

    private static class GameField extends JPanel{
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
            isActive = true;
            game_window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            game_window.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    isActive = false;
                    game_window.dispose();
                    panel1.setVisible(true);
                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
            game_window.setLocation(500, 100);
            game_window.setSize(906, 478);
            game_window.setResizable(false);
            isActive = true;
            last_frame_time = System.nanoTime();
            game_window.setTitle("Score = " + score + " live = " + live);
            game_window.add(game_Field);
            game_window.setVisible(true);
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
