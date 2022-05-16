import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Menu extends JFrame {
    private JTextField szerokosc;
    private JTextField wysokosc;
    Swiat swiat;
    private int loadGame = 0;

    Menu(){
        setTitle(" Wirtualny swiat | Bartosz Bartczak 188848");

        JButton start = new JButton("Nowa gra");
        start.setBounds(150,400,300,150);
        start.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton load = new JButton("Wczytaj gre");
        load.setBounds(500,400,300,150);
        load.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton startHex = new JButton("Nowa gra HEX");
        startHex.setBounds(150,575,300,150);
        startHex.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton loadHex = new JButton("Wczytaj gre HEX");
        loadHex.setBounds(500,575,300,150);
        loadHex.setFont(new Font("Arial", Font.PLAIN, 30));

        szerokosc = new JTextField("");
        szerokosc.setBounds(500,200,200,100);
        szerokosc.setFont(new Font("Arial", Font.PLAIN, 50));

        wysokosc = new JTextField("");
        wysokosc.setBounds(200,200,200,100);
        wysokosc.setFont(new Font("Arial", Font.PLAIN, 50));

        JLabel szerLabel = new JLabel("Szerokosc");
        szerLabel.setBounds(220,125,200,100);
        szerLabel.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel wysLabel = new JLabel("Wysokosc");
        wysLabel.setBounds(520,125,200,100);
        wysLabel.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel newGame = new JLabel("Wirtualny Swiat");
        newGame.setBounds(300,25,800,100);
        newGame.setFont(new Font("Arial", Font.PLAIN, 50));


        add(start);
        add(load);
        add(loadHex);
        add(startHex);
        add(szerokosc);
        add(wysokosc);
        add(szerLabel);
        add(wysLabel);
        add(newGame);
        setSize(1000,800);
        setLayout(null);
        setVisible(true);

        start.addActionListener(e -> {
            startGame();
        });

        startHex.addActionListener(e -> {
            startGameHex();
        });

        load.addActionListener(e -> {
            loadGame = 1;
            startGame();
        });

        loadHex.addActionListener(e -> {
            loadGame = 1;
            startGameHex();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startGame(){
        this.dispose();

        if (loadGame == 1) {
            try {
                swiat = new Swiat(10,10);
                swiat.wczytajGre(swiat, "normal");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else {

            int szer = Integer.parseInt(szerokosc.getText());
            int wys = Integer.parseInt(wysokosc.getText());

            swiat = new Swiat(szer,wys);

            swiat.przygotujSwiat(swiat, 0);
        }

        new Game(swiat, swiat.isHex);
    }

    private void startGameHex(){
        this.dispose();

        if (loadGame == 1) {
            try {
                swiat = new Swiat(10,10);
                swiat.wczytajGre(swiat, "hex");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else {
            int szer = Integer.parseInt(szerokosc.getText());
            int wys = Integer.parseInt(wysokosc.getText());

            swiat = new Swiat(szer, wys);

            swiat.przygotujSwiat(swiat, 1);

        }
        new Game(swiat, swiat.isHex);

    }
}
