
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
/* @author Mihail Kozlov
       *create by Mihail Kozlov 30.12.2020
       * My game catch a drop 
       */

public class GameWindow extends JFrame {
    private static long last_frame_time;
    private static GameWindow game_window;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static int volume= 7;
    private static int volumeBackground= 8;
    private static float drop_left = 400;
    private static float drop_top = -100;
    protected static float drop_v = 200;
    protected static float drop_vy = 20;
    protected static int multiple = 2;
    private static float drop_vy1 ;
    private static int score = 0;
    private static boolean isActive = false;
    protected static int live = 3;


    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        SwingApp.switchMenu();
        Sound.repeat("fon.wav",0.1f*volumeBackground,6);
    }

    public static void setVolume(int volume) {
        GameWindow.volume = volume;
    }

    public static int getVolume() {
        return volume;
    }

    public static void setVolumeBackground(int volumeBackground) {
        GameWindow.volumeBackground = volumeBackground;
    }

    public static int getVolumeBackground() {
        return volumeBackground;
    }

    public static void change(){
        drop_left = 400;
        drop_top = -100;
        drop_v = 200;
        drop_vy = 20 ;
        score = 0;
        live = 3;
    }

    private static void onRepaint(Graphics g) throws FileNotFoundException {
        long current_time = System.nanoTime();
        float drop_right = drop_left + drop.getWidth(null);
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        boolean is_drop1 = 0 > drop_left || drop_right > 906;
        if (is_drop1) {
            drop_vy1 = drop_vy1 * -1;
        }
        drop_top = drop_top + drop_v * delta_time;
        drop_left = drop_left + drop_vy1 * delta_time;
        g.drawImage(backGround, -253, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (live <= 0) {
            g.drawImage(gameOver, 280, 120, null);
            JTextPane textPane = new JTextPane();
            textPane.setBounds(50 ,0,200,250);
            Font font = new Font("Jane",Font.BOLD,15);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("Game Over! Your score is " + score ,340 ,70);

            g.drawString("Click on the 'Game Over' to go to the menu ", 290,110);
            drop_v = 0;
            drop_vy1 = 0;
            drop_top = -100;
        }
        if (drop_top > game_window.getHeight()) {
            live = live - 1;
            new Sound("fail.wav",0.1f*volume);
            Random rand = new Random();
            int r = rand.nextInt(20) - 10;
            drop_top = -100;
            drop_left = (int) (Math.random() * ((game_window.getWidth()) - drop.getWidth(null)));
            if (drop_v>=400) {
                drop_v = drop_v - 200;
            }else {
                drop_v = 200;
            }
            drop_vy1 = drop_vy * r;
            game_window.setTitle("Score = " + score + " live = " + live);
            if (live == 0) {
                new Sound("death.wav",0.1f*volume);
            }
        }
        game_window.setTitle("Score = " + score + " live = " + live);
    }

    public static boolean getActive(){
        return isActive;
    }

    public static void sort() throws FileNotFoundException {
        int a;
        int b;
        ClassLoader classLoader = GameWindow.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("Score.txt")).getFile());
        Scanner scanner = new Scanner(file);
        int[] result = new int[10];
        for (int i = 0; i < result.length;i++){
            result[i] =scanner.nextInt();
        }
        scanner.close();
        for (int i = 0;i <result.length; i++){
            if (result[i] > score ){
                continue;
            }else if(result[result.length-1] > score) {
                break;
            }else{
                a = result[i];
                result[i]=score;
                for (int j = i+1; j < result.length ; j++){
                    b = result[j];
                    result[j] = a;
                    a = b;
                }
                break;
            }
        }

        PrintWriter pw = new PrintWriter(String.valueOf(file));
        for (int i = 0; i < result.length;i++){
            pw.println(result[i]);
        }
        pw.close();
    }


    protected static class GameField extends JPanel{
        private static boolean lvlUp=false;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//            if (score < 10) {
                try {
                    onRepaint(g);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//
//            }else if(score == 10){
//                g.drawImage(backGround, -253, 0, null);
//                g.drawString("Hello",100,100);
//                drop_left = 100;
//                drop_top = 200;
//                g.drawImage(drop, (int) drop_left, (int) drop_top, null);
//
//            }else if (score > 10){
//                change();
//                try {
//                    onRepaint(g);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
                repaint();
//            }
        }
        protected static void start(GameField game_Field) throws IOException {
            JFrame panel1 = SwingApp.getPanel1();
            backGround = ImageIO.read(GameWindow.class.getResourceAsStream("BackGround.png"));
            gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
            drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
            game_window = new GameWindow();
            game_window.setIconImage(drop);
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
                    try {
                        sort();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    change();

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
                    float gameOver_right = 280 + gameOver.getWidth(null);
                    float gameOver_bottom = 120 + gameOver.getHeight(null);
                    boolean lvlUp1 = x >= 279 && x<= gameOver_right && y <=gameOver_bottom && y >=120;
                    if (lvlUp1 &&score == 10){
                        lvlUp = true;
                    }
                    boolean is_Game_Over = x >= 280 && x<= gameOver_right && y <=gameOver_bottom && y >=120;
                    if (is_Game_Over && live <= 0){
                        isActive = false;
                        game_window.dispose();
                        panel1.setVisible(true);
                        try {
                            sort();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        change();
                    }
                    float drop_right = drop_left + drop.getWidth(null);
                    float drop_bottom = drop_top + drop.getHeight(null);
                    boolean is_drop = x >= drop_left && x <= drop_right && y <= drop_bottom && y >= drop_top;
                    if (is_drop) {
                        Random rand = new Random();
                        int r = rand.nextInt(20) - 10;
                        new Sound("zvuk-kapli.wav", 0.1f*volume);
                        drop_top = -100;
                        drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                        drop_v = drop_v + 20;
                        drop_vy = drop_vy + multiple;
                        drop_vy1 =drop_vy* r;
                        score += 1;
                        game_window.setTitle("Score = " + score + " live = " + live);
                    }
                }
            });
        }
    }

}
