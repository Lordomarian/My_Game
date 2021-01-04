import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public  class Menu extends JPanel {
    static boolean isActive;
    private static Image backGround;
    private static Image gameOver;
    private static Image drop;
    private static Image start_Menu;

    public static boolean isActive() {
        return isActive;
    }
    public static String menuType = "A";
    public static void destroy() {/* удаляет все ненужное из памяти */}

    public static void initMenu(

    ) {/* инициализация первого меню*/}

    private static void initRest() {/* инициализация остальной части меню */}
    @Override
    public  void paint(Graphics g) {
        super.paint(g);

        isActive = true;
        try {
            drawMenuBackground(g);
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch(menuType){
            case "A":
                try {
                    showMenuA(g);
                    wait(100000);
                    MyCanvas.stop();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "B":
                try {
                    showMenuB(g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private static void drawMenuBackground(Graphics g) throws IOException {
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("BackGround.png"));
        g.drawImage(backGround, 0, 0, null);
    }

    public static void processKey(int keyCode, int GameActionKey){/* обработка клавиш */}


    private static void showMenuA(Graphics g) throws IOException {
        start_Menu = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        g.drawImage(start_Menu, 300, 150, null);
    }
    private static void showMenuB(Graphics g) throws IOException {
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        g.drawImage(gameOver, 280, 120, null);
    }

}
class Game extends JPanel{
    static boolean isActive;

    public static boolean isActive(){return isActive;}

    public static void destroy(){/* удаляем все лишнее */}

    public static void initGame(){/* инициализация игры */}

    public  void paint(Graphics g) {
        super.paint(g);

// игровое действие
        isActive=true;
    }
    public static void processKey(int keyCode, int GameActionKey){
        /* обработка клавиш */
    }
}
