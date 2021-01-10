
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
    private static Image nextLvl;
    private static Image win;
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
    private static int lvl = 1 ;
    private static boolean isWin= false;
    private static boolean isActive = false;
    private static boolean isLvl= true;
    private static boolean isLvl1= false;
    protected static int live = 3;
    private static boolean s = true;


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
        isLvl= true;
        isLvl1= false;
        live = 3;
        s = true;
    }
    private static void onRepaint(Graphics g) throws FileNotFoundException {
        long current_time = System.nanoTime();
        Font font = new Font("Jane",Font.BOLD,15);
        g.setFont(font);
        g.setColor(Color.RED);
        float drop_right = drop_left + drop.getWidth(null);
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        g.drawImage(backGround, -253, 0, null);
        boolean is_drop1 = 0 > drop_left || drop_right > 906;
        if (is_drop1) {
            drop_vy1 = drop_vy1 * -1;
        }
        if (!SwingApp.isArcade){
                    if (score == 10 && isLvl ){
                        g.drawString("You win lvl 1  click on for play lvl 2  ",340,90);
                        drop_left = 400;
                        drop_top = -200;
                        lvl = 2 ;
                        isLvl1 = true;
                        g.drawImage(nextLvl,430,140,null);
                    }else if (score == 25 && isLvl1 ){
                        g.drawString("You win lvl 2  click on for play lvl 3  ",340,70);
                        drop_left = 400;
                        drop_top = -200;
                        lvl = 3 ;
                        g.drawImage(nextLvl,430,140,null);
                    }else if (score == 40){

                        g.drawString("Congratulation! YOU WIN the game ",320,70);
                        g.drawString("if u want play arcade press checkbox in settings", 280 ,95);
                        drop_left = 400;
                        drop_top = -200;
                        g.drawImage(win, 345, 120, null);
                        isWin = true;
                        if (s) {
                            new Sound("win.wav", 0.1f * volume);
                            s = false;
                        }
                    }
        }
        drop_top = drop_top + drop_v * delta_time;
        drop_left = drop_left + drop_vy1 * delta_time;
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (live <= 0) {
            g.drawImage(gameOver, 280, 120, null);
            g.drawString("Game Over! Your score is " + score * SwingApp.i ,340 ,70);
            g.drawString("    Difficulty is " + SwingApp.difficulty, 370,90);
            g.drawString("Click on the 'Game Over' to go to the menu ", 290,110);
            g.drawString("if u want play arcade press checkbox in settings", 275 ,370);
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

    public static void sort() throws IOException {
        int a;
        int b;
        InputStream in = GameWindow.class.getResourceAsStream("/Score.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        ClassLoader classLoader = GameWindow.class.getClassLoader();
        //File file = new File(Objects.requireNonNull(classLoader.getResource("Score.txt")).getFile());
       // Scanner scanner = new Scanner(file);
        int[] result = new int[10];
        for (int i = 0; i < result.length;i++){
            result[i] = Integer.parseInt(reader.readLine());
            System.out.println(result[i]);
        }
        //scanner.close();
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

        PrintWriter pw = new PrintWriter(String.valueOf(in));
        //OutputStream os = GameWindow.class.getResourceAsStream("/Score.txt");
        //try (BufferedWriter bw = new BufferedWriter()) {
        //}
        for (int i = 0; i < result.length;i++){

        pw.close();
            }
    }


    protected static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
                try {
                    onRepaint(g);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                repaint();
        }
        protected static void start(GameField game_Field) throws IOException {
            JFrame panel1 = SwingApp.getPanel1();
            if (SwingApp.difficulty.equals("hard")&&!SwingApp.isArcade){
                live =2;
            }
            nextLvl =ImageIO.read((GameWindow.class.getResourceAsStream("nextlvl (1).png")));
            win = ImageIO.read((GameWindow.class.getResourceAsStream("crown.png")));
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
                    isWin = false;
                    isLvl= true;
                    isLvl1= false;
                    score= score*SwingApp.i;

                    game_window.dispose();
                    panel1.setVisible(true);
                    try {
                        sort();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
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
                    boolean lvlUp1 = x >= 345 && x<= 345+win.getWidth(null) && y <=120+win.getHeight(null) && y >=120;

                    if (isWin&&lvlUp1){
                        isWin = false;
                        isActive = false;
                        isLvl= true;
                        isLvl1= false;
                        game_window.dispose();
                        panel1.setVisible(true);
                        score = score * SwingApp.i;
                        try {
                            sort();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        change();
                    }
                    boolean lvlUp = x >= 430 && x<= 430+nextLvl.getWidth(null) && y <=140+nextLvl.getHeight(null) && y >=140;
                    if ((lvl == 2 ) && lvlUp){
                        lvl = 0 ;
                        Random rand = new Random();
                        int r = rand.nextInt(20) - 10;
                        isLvl = false;
                        live+=1;
                        drop_top = -150;
                        drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                        drop_v = drop_v + 100;
                        drop_vy = drop_vy + 2*multiple;
                        drop_vy1 = drop_vy * r;
                    }
                    if ((lvl == 3 ) && lvlUp){
                        Random rand = new Random();
                        lvl = 0;
                        int r = rand.nextInt(20) - 10;
                        isLvl1 = false;
                        live+=1;
                        drop_top = -150;
                        drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                        drop_v = drop_v + 100;
                        drop_vy = drop_vy + 3*multiple;
                        drop_vy1 = drop_vy * r;
                    }
                }
            });
            game_Field.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    int x = e.getX();
                    int y = e.getY();
                    float gameOver_right = 280 + gameOver.getWidth(null);
                    float gameOver_bottom = 120 + gameOver.getHeight(null);


                    boolean is_Game_Over = x >= 280 && x <= gameOver_right && y <= gameOver_bottom && y >= 120;
                    if (is_Game_Over && live <= 0) {
                        isActive = false;
                        game_window.dispose();
                        score = score*SwingApp.i;
                        panel1.setVisible(true);
                        try {
                            sort();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        change();
                    }
                    float drop_right = drop_left + drop.getWidth(null);
                    float drop_bottom = drop_top + drop.getHeight(null);
                    boolean is_drop = x >= drop_left && x <= drop_right && y <= drop_bottom && y >= drop_top;
                    if (is_drop) {
                        Random rand = new Random();
                        int r = rand.nextInt(20) - 10;
                        new Sound("zvuk-kapli.wav", 0.1f * volume);
                        drop_top = -150;
                        drop_left = (int) (Math.random() * ((game_Field.getWidth()) - drop.getWidth(null)));
                        drop_v = drop_v + 20;
                        drop_vy = drop_vy + multiple;
                        drop_vy1 = drop_vy * r;
                        score += 1;
                        game_window.setTitle("Score = " + score + " live = " + live);

                    }

                }
            });
        }
    }

}
