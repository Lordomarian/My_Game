import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.util.Scanner;

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
                    return;
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
    private static void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

       // int len = tp.getDocument().getLength();
        //tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
    private static void showMenuB(Container panel) throws FileNotFoundException {
        panel.setLayout(null);

        JTextPane textArea = new JTextPane();
        textArea.setBounds(50 ,0,200,250);

        String sb = ("             Best Results \n");
        
        ClassLoader classLoader = SwingApp.class.getClassLoader();
        File file = new File(classLoader.getResource("Score.txt").getFile());
        Scanner scanner = new Scanner(file);
        int[] mass = new  int[10];
        for (int i = 0; i < mass.length;i++){
            mass[i] =scanner.nextInt();
            String str = Integer.toString(mass[i]);

            sb = sb + "                 "+ +  (1 + i)  + "   -   " + str + "\n";
        }
        scanner.close();

        Font font = new Font("Vardana",Font.BOLD,16);
        textArea.setFont(font);
        appendToPane(textArea,sb,Color.green);
        textArea.setSelectionColor(new Color(53, 53, 73));
        textArea.setBackground(new Color(53, 53, 73));
        textArea.setText(sb);

        textArea.setEditable(false);
        panel.add(textArea);

        JButton quitButton = new JButton("Back");

        quitButton.setPreferredSize(new Dimension(100, 90));
        Insets insets = panel.getInsets();
        Dimension size = quitButton.getPreferredSize();
        quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);
        panel.add(quitButton);

        ActionListener quit = new QuitActionListener();
        quitButton.addActionListener(quit);

    }


    private static void showMenuC(Container panel) {
        panel.setLayout(null);


        JButton quitButton = new JButton("Back");
        JButton plusButton = new JButton("+");
        JButton minesButton = new JButton("-");
        JButton plusButton1 = new JButton("+");
        JButton minesButton1 = new JButton("-");

        JTextPane textArea = new JTextPane();
        JTextPane textArea1 = new JTextPane();
        String volume =  "      Effect volume \n               " + String.valueOf(10*GameWindow.getVolume())+ " %";
        String volumeBackground = " Background volume\n               " + String.valueOf(10*GameWindow.getVolumeBackground())+ " %";
        appendToPane(textArea,volume,Color.yellow);
        appendToPane(textArea1,volumeBackground,Color.YELLOW);
        textArea.setBounds(90 ,10,120,45);
        textArea1.setBounds(90 ,60,120,45);
        textArea.setEditable(false);
        textArea1.setEditable(false);
        textArea.setText(volume);
        textArea1.setText(volumeBackground);
        panel.add(textArea);
        panel.add(textArea1);

        textArea.setSelectionColor(new Color(53, 53, 73));
        textArea1.setSelectionColor(new Color(53, 53, 73));
        textArea.setBackground(new Color(53, 53, 73));
        textArea1.setBackground(new Color(53, 53, 73));
        quitButton.setPreferredSize(new Dimension(100, 90));
        Insets insets = panel.getInsets();
        Dimension size = quitButton.getPreferredSize();

        minesButton1.setBounds(25 + insets.left, 70 + insets.top, size.width -50, size.height - 50);
        plusButton1.setBounds(225 + insets.left, 70 + insets.top, size.width -50, size.height - 50);
        minesButton.setBounds(25 + insets.left, 20 + insets.top, size.width -50, size.height - 50);
        plusButton.setBounds(225 + insets.left, 20 + insets.top, size.width -50, size.height - 50);
        quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);

        panel.add(plusButton);
        panel.add(quitButton);
        panel.add(minesButton);
        panel.add(plusButton1);
        panel.add(minesButton1);

        plusButton.addActionListener(e -> {
            if (GameWindow.getVolume() < 10 ){
                GameWindow.setVolume(GameWindow.getVolume()+1);
                String volume1 = "      Effect volume \n               " + String.valueOf(10*GameWindow.getVolume())+ " %";
                new Sound("zvuk-kapli.wav", 0.1f*GameWindow.getVolume());
                textArea.setText(volume1);
            }
        });
        minesButton.addActionListener(e -> {
            if (GameWindow.getVolume() >0 ){
                GameWindow.setVolume(GameWindow.getVolume()-1);
                String volume1 = "      Effect volume \n               " + String.valueOf(10*GameWindow.getVolume())+ " %";
                new Sound("zvuk-kapli.wav", 0.1f*GameWindow.getVolume());
                textArea.setText(volume1);
            }
        });
        plusButton1.addActionListener(e -> {
            if (GameWindow.getVolumeBackground() < 10 ){
                GameWindow.setVolumeBackground(GameWindow.getVolumeBackground()+1);
                Sound.setVolume1(0.1f*GameWindow.getVolumeBackground());
                String volumeBackground1 = " Background volume\n               " + String.valueOf(10*GameWindow.getVolumeBackground())+ " %";
                textArea1.setText(volumeBackground1);
            }
        });
        minesButton1.addActionListener(e -> {
            if (GameWindow.getVolumeBackground() >0 ){
                GameWindow.setVolumeBackground(GameWindow.getVolumeBackground()-1);
                Sound.setVolume1(0.1f*GameWindow.getVolumeBackground());
                String volumeBackground1 = " Background volume\n               " + String.valueOf(10*GameWindow.getVolumeBackground())+ " %";
                textArea1.setText(volumeBackground1);
            }
        });
        ActionListener quit = new QuitActionListener();
        quitButton.addActionListener(quit);
    }
    private static void showMenuD(Container panel) {
        panel.setLayout(null);

        JTextPane textPane = new JTextPane();
        textPane.setBounds(50 ,0,200,250);
        String s = new String("\n\n              Created by: \n \n             Lordomarian \n\n \n\n \n\n\n                (с) 2020");

        panel.add(textPane);
        Font font = new Font("Vardana",Font.BOLD,15);
        textPane.setFont(font);
        appendToPane(textPane,s,Color.red);
        textPane.setText(s);
        textPane.setSelectionColor(new Color(53, 53, 73));
        textPane.setEditable(false);
        textPane.setBackground(Color.getColor(s));

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