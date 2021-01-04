import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

public  class Test extends   JFrame  {
    private static Graphics graphics;
    private static boolean i = true;
    private static long last_frame_time;
    public static GameWindow game_window;
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
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("BackGround.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        game_window.setTitle("Score = " + score + " live = " + live);
        MyCanvas canvas = new MyCanvas();
        game_window.setVisible(true);
        canvas.run();


    }
}
