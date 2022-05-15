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
        szerokosc.setBounds(200,200,200,100);
        wysokosc.setBounds(500,200,200,100);
        szerokosc.setFont(new Font("Arial", Font.PLAIN, 30));
        wysokosc.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel szerLabel = new JLabel("Szerokosc");
        JLabel wysLabel = new JLabel("wysokosc");
        szerLabel.setBounds(275,125,200,100);
        wysLabel.setBounds(575,125,200,100);

        add(start);
        add(load);
        add(szerokosc);
        add(wysokosc);
        add(szerLabel);
        add(wysLabel);
        setSize(1000,800);
        setLayout(null);
        setVisible(true);

        start.addActionListener(e -> {
            startGame();
        });

        load.addActionListener(e -> {
            loadGame = 1;
            startGame(); //TODO: breakpoint
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startGame(){
        this.dispose();

        int szer = Integer.parseInt(szerokosc.getText());
        int wys = Integer.parseInt(wysokosc.getText());

        Swiat swiat = new Swiat(szer,wys);

        if (loadGame == 1) {
            try {
                swiat.wczytajGre(swiat);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else {
            swiat.przygotujSwiat(swiat);
        }

        new Game(swiat);
    }
}
