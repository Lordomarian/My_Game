
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;


public class SwingApp extends JFrame {

    public static JFrame getPanel1() {
        return panel1;
    }

    protected static String difficulty = "easy";
    protected static boolean isArcade = false;
    protected static int i = 1;

    private static String menuType = "A1";
    private static final JFrame  panel1 = new JFrame();
    private static final JCheckBox jCheckBox = new JCheckBox();
    private static final JCheckBox arcadeCheckBox = new JCheckBox();


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
                if (!isActive) {
                    switch (difficulty) {
                        case "easy":
                            GameWindow.drop_vy = 0;
                            GameWindow.multiple = 0;
                            break;
                        case "norm":
                            i=2;
                            break;
                        case "hard":
                            i=3;
                            GameWindow.drop_v = 500;
                            GameWindow.drop_vy = 50;
                            break;
                    }
                    isArcade = arcadeCheckBox.isSelected();
                    panel1.setVisible(jCheckBox.isSelected());
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

    protected static void createAndShowGUI() throws IOException {
        panel1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1.setResizable(false);
        BufferedImage drop;
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        panel1.setIconImage(drop);
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

    private static void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    private static void showMenuB(Container panel) {
        panel.setLayout(null);

        JTextPane textArea = new JTextPane();
        textArea.setBounds(50 ,0,200,250);

        StringBuilder sb = new StringBuilder("             Best Results \n");
        GameWindow.readScore();
        int k = 1;
        for (Integer score : GameWindow.scoreList) {
            sb.append("                 ").append(k++).append("   -   ").append(score).append("\n");
        }

        Font font = new Font("Vardana",Font.BOLD,16);
        textArea.setFont(font);
        appendToPane(textArea,sb.toString(),Color.green);
        textArea.setSelectionColor(new Color(53, 53, 73));
        textArea.setBackground(new Color(53, 53, 73));
        textArea.setText(sb.toString());

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
        JButton plusButton2 = new JButton("+");
        JButton minesButton2 = new JButton("-");
        jCheckBox.setBounds(41 , 125 ,21 ,20 );
        arcadeCheckBox.setBounds(41 , 215 ,21 ,20 );
        JTextPane textArea = new JTextPane();
        JTextPane textArea1 = new JTextPane();
        JTextPane textArea2 = new JTextPane();
        JTextPane textCheckBox = new JTextPane();
        JTextPane textArcadeCheckBox = new JTextPane();
        String gameLvl = "            Difficulty \n               " + difficulty;
        String arcade = "        Play arcade?";
        String checkBox = "Show menu while playing";
        String volume =  "      Effect volume \n               " + 10*GameWindow.getVolume() + " %";
        String volumeBackground = " Background volume\n               " + 10*GameWindow.getVolumeBackground() + " %";
        appendToPane(textArea,volume,Color.yellow);
        appendToPane(textArea2,gameLvl,Color.yellow);
        appendToPane(textArea1,volumeBackground,Color.YELLOW);
        appendToPane(textCheckBox,checkBox,Color.YELLOW);
        appendToPane(textArcadeCheckBox,arcade,Color.YELLOW);
        textCheckBox.setBounds(80 ,123,170,30);
        textArea.setBounds(90 ,10,120,45);
        textArea1.setBounds(90 ,60,120,45);
        textArea2.setBounds(90,155,120,45);
        textArcadeCheckBox.setBounds(90, 213,120,30);

        textArea.setEditable(false);
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        textArcadeCheckBox.setEditable(false);
        textCheckBox.setEditable(false);


        panel.add(textArea);
        panel.add(textArea1);
        panel.add(textArea2);
        panel.add(textCheckBox);
        panel.add(textArcadeCheckBox);

        textArea.setSelectionColor(new Color(53, 53, 73));
        textArea1.setSelectionColor(new Color(53, 53, 73));
        textArea2.setSelectionColor(new Color(53, 53, 73));
        textCheckBox.setSelectionColor(new Color(53, 53, 73));
        textArcadeCheckBox.setSelectionColor(new Color(53, 53, 73));

        textArea.setBackground(new Color(53, 53, 73));
        textArea1.setBackground(new Color(53, 53, 73));
        textArea2.setBackground(new Color(53, 53, 73));
        textCheckBox.setBackground(new Color(53, 53, 73));
        textArcadeCheckBox.setBackground(new Color(53, 53, 73));

        quitButton.setPreferredSize(new Dimension(100, 90));
        Insets insets = panel.getInsets();
        Dimension size = quitButton.getPreferredSize();

        minesButton.setBounds(25 + insets.left, 20 + insets.top, size.width -50, size.height - 50);
        plusButton.setBounds(225 + insets.left, 20 + insets.top, size.width -50, size.height - 50);
        minesButton1.setBounds(25 + insets.left, 70 + insets.top, size.width -50, size.height - 50);
        plusButton1.setBounds(225 + insets.left, 70 + insets.top, size.width -50, size.height - 50);
        minesButton2.setBounds(25 + insets.left, 160 + insets.top, size.width -50, size.height - 50);
        plusButton2.setBounds(225 + insets.left, 160 + insets.top, size.width -50, size.height - 50);
        quitButton.setBounds(25 + insets.left, 250 + insets.top, size.width + 150, size.height - 50);

        panel.add(plusButton);
        panel.add(minesButton);
        panel.add(plusButton1);
        panel.add(minesButton1);
        panel.add(plusButton2);
        panel.add(minesButton2);
        panel.add(jCheckBox);
        panel.add(arcadeCheckBox);
        panel.add(quitButton);

        plusButton.addActionListener(e -> {
            if (GameWindow.getVolume() < 10 ){
                GameWindow.setVolume(GameWindow.getVolume()+1);
                String volume1 = "      Effect volume \n               " + 10*GameWindow.getVolume() + " %";
                new Sound("zvuk-kapli.wav", 0.1f*GameWindow.getVolume());
                textArea.setText(volume1);
            }
        });
        minesButton.addActionListener(e -> {
            if (GameWindow.getVolume() >0 ){
                GameWindow.setVolume(GameWindow.getVolume()-1);
                String volume1 = "      Effect volume \n               " + 10*GameWindow.getVolume() + " %";
                new Sound("zvuk-kapli.wav", 0.1f*GameWindow.getVolume());
                textArea.setText(volume1);
            }
        });
        plusButton1.addActionListener(e -> {
            if (GameWindow.getVolumeBackground() < 10 ){
                GameWindow.setVolumeBackground(GameWindow.getVolumeBackground()+1);
                Sound.setVolume1(0.1f*GameWindow.getVolumeBackground());
                String volumeBackground1 = " Background volume\n               " + 10*GameWindow.getVolumeBackground() + " %";
                textArea1.setText(volumeBackground1);
            }
        });
        minesButton1.addActionListener(e -> {
            if (GameWindow.getVolumeBackground() >0 ){
                GameWindow.setVolumeBackground(GameWindow.getVolumeBackground()-1);
                Sound.setVolume1(0.1f*GameWindow.getVolumeBackground());
                String volumeBackground1 = " Background volume\n               " + 10*GameWindow.getVolumeBackground() + " %";
                textArea1.setText(volumeBackground1);
            }
        });
        plusButton2.addActionListener(e -> {
            if (difficulty.equals("easy")){
                difficulty = "norm";
                String gameLvl1 = "            Difficulty \n               " + difficulty;
                textArea2.setText(gameLvl1);
            }else if(difficulty.equals("norm")){
                difficulty ="hard";
                String gameLvl1 = "            Difficulty \n               " + difficulty;
                textArea2.setText(gameLvl1);
            }
        });
        minesButton2.addActionListener(e -> {
            if (difficulty.equals("hard") ){
                difficulty = "norm";
                String gameLvl1 = "            Difficulty \n               " + difficulty;
                textArea2.setText(gameLvl1);

            }else if( difficulty.equals("norm")){
                difficulty = "easy";
                String gameLvl1 = "            Difficulty \n               " + difficulty;
                textArea2.setText(gameLvl1);
            }
        });

        ActionListener quit = new QuitActionListener();
        quitButton.addActionListener(quit);
    }

    private static void showMenuD(Container panel) {
        panel.setLayout(null);

        JTextPane textPane = new JTextPane();
        textPane.setBounds(50 ,0,200,250);
        String s = "\n\n              Created by: \n \n             Lordomarian \n\n               Bouncer77 \n\n \n\n\n                (с) 2020";

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