import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Menu extends JFrame {
    private JFrame f;
    private JTextField szerokosc;
    private JTextField wysokosc;
    Swiat swiat;
    private int loadGame = 0;

    Menu(){
        setTitle(" Wirtualny swiat | Bartosz Bartczak 188848");
        JButton start = new JButton("Nowa gra");
        JButton load = new JButton("Wczytaj gre");
        start.setBounds(150,400,300,150);
        load.setBounds(500,400,300,150);
        start.setFont(new Font("Arial", Font.PLAIN, 30));
        load.setFont(new Font("Arial", Font.PLAIN, 30));

        szerokosc = new JTextField("");
        wysokosc = new JTextField("");
        wysokosc.setBounds(200,200,200,100);
        szerokosc.setBounds(500,200,200,100);
        szerokosc.setFont(new Font("Arial", Font.PLAIN, 50));
        wysokosc.setFont(new Font("Arial", Font.PLAIN, 50));

        JLabel szerLabel = new JLabel("Szerokosc");
        JLabel wysLabel = new JLabel("Wysokosc");
        szerLabel.setBounds(220,125,200,100);
        wysLabel.setBounds(520,125,200,100);
        szerLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        wysLabel.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel newGame = new JLabel("Wirtualny Swiat");
        newGame.setBounds(300,25,800,100);
        newGame.setFont(new Font("Arial", Font.PLAIN, 50));

        add(start);
        add(load);
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

        load.addActionListener(e -> {
            loadGame = 1;
            startGame();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startGame(){
        this.dispose();

        if (loadGame == 1) {
            try {
                swiat = new Swiat(10,10);
                swiat.wczytajGre(swiat);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else {

            int szer = Integer.parseInt(szerokosc.getText());
            int wys = Integer.parseInt(wysokosc.getText());

            swiat = new Swiat(szer,wys);

            swiat.przygotujSwiat(swiat);
        }

        new Game(swiat);
    }
}
