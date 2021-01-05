import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class SwingApp extends JFrame {
    private static String menuType = "A1";
    private static JFrame  panel1 = new JFrame();

    public static JFrame getPanel1() {
        return panel1;
    }

    protected static void addComponentsToPanel(Container panel) {
        panel.setLayout(null);

        JButton quitButton = new JButton("Exit");
        JButton scoreButton = new JButton("Score");
        JButton startButton = new JButton("Start");
        JButton settingButton = new JButton("Settings");
        JButton creditButton = new JButton("Credit");

        startButton.addActionListener(e -> {
            GameWindow.GameField gameField = new GameWindow.GameField();
            boolean isActive = GameWindow.getActive();
            try {
                if (isActive) {
                } else {
                    panel1.setVisible(false);
                    GameWindow.GameField.start(gameField);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        quitButton.addActionListener(e -> System.exit(0));
        scoreButton.addActionListener(e -> {
            menuType = "B";
            try {
                switchMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        creditButton.addActionListener(e -> {
            menuType = "D";
            try {
                switchMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        settingButton.addActionListener(e -> {
            menuType = "C";
            try {
                switchMenu();
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
        addComponentsToPanel(panel1.getContentPane());
        panel1.setSize(305, 340);
    }
    protected static void switchMenu() throws IOException {
        switch (menuType) {
            case "A1"://menu
                createAndShowGUI();
                panel1.setTitle("Menu");
                break;
            case "A":
                panel1.setTitle("Menu");
                panel1.getContentPane().removeAll();
                panel1.revalidate();
                panel1.repaint();
                addComponentsToPanel(panel1.getContentPane());
                break;
            case "B"://score
                panel1.setTitle("Score");
                panel1.getContentPane().removeAll();
                panel1.revalidate();
                panel1.repaint();
                showMenuB(panel1.getContentPane());
                break;
            case "C"://settings
                panel1.setTitle("Settings");
                panel1.getContentPane().removeAll();
                panel1.revalidate();
                panel1.repaint();
                showMenuC(panel1.getContentPane());
                break;
            case "D"://credit
                panel1.setTitle("Credit");
                panel1.getContentPane().removeAll();
                panel1.revalidate();
                panel1.repaint();
                showMenuD(panel1.getContentPane());
                break;
        }
    }
    private static void showMenuB(Container panel) throws FileNotFoundException {
        panel.setLayout(null);
        String sb = new String("                   Best Results \n");
        JTextArea textArea = new JTextArea();
        textArea.setBounds(50 ,0,200,250);
        panel.add(textArea);
        File file = new File("src/main/resources/Score.txt");
        Scanner scanner = new Scanner(file);
        int[] mass = new  int[10];
        for (int i = 0; i < mass.length;i++){
            mass[i] =scanner.nextInt();
            String str = Integer.toString(mass[i]);

            sb = sb + "                    "+ +  (1 + i)  + "   -   " + str + "\n";
        }
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        scanner.close();

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

    /*            quitButton.addActionListener(new ActionListener() {
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
    */       }


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