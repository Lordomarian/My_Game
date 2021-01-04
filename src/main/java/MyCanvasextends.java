import java.awt.*;
import java.io.IOException;

class MyCanvas extends Canvas implements Runnable {
    private static Graphics graphics;
    private static boolean running =true;
    public static boolean inMenu=true;
    public static boolean doInit=true;

    @Override
    public void run() {
        try {
            while (running) {
                if (doInit) {
                    doInit = false;
                    if (inMenu) {
                        Menu menu = new Menu();
                        Menu.initMenu();
                        Test.game_window.add(menu);
                    } else {
                        Game game = new Game();

                        Game.initGame();
                        Test.game_window.add(game);

                    }
                    }if(inMenu){
                        if(!Menu.isActive()){
                            doInit=true;
                            inMenu=false;
                            Menu.destroy();
                    }
                }else{
                    if(!Game.isActive()){
                        inMenu=true;
                        doInit=true;
                        Game.destroy();
                    }

                }

                repaint();
            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {System.out.println("finally");}
    }

                //Главный игровой цикл



//    public void paint(Graphics g){
//
//        if(inMenu) {
//            try {
//                //Menu.paint(g);
//            } catch (IOException e) {
//               // e.printStackTrace();
//            }
//        }
//        else Game.paint(g);
//
//    }
    synchronized static void start(){
        running=true;
    }
    synchronized static void stop(){
        running=false;
    }
}
