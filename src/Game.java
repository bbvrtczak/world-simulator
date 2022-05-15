import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private JFrame f;

    private JLabel comments;
    private JPanel commentsBg;
    private JPanel board;
    Swiat swiat;

    Game(Swiat s){
        swiat = s;

        JButton nextTurn = new JButton("Nastepna tura");
        nextTurn.setBounds(1100,950,300,150);
        nextTurn.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton saveGame = new JButton("Zapisz gre");
        saveGame.setBounds(700,950,300,150);
        saveGame.setFont(new Font("Arial", Font.PLAIN, 30));

        comments = new JLabel();
        comments.setBounds(1100,100,300,800);
        comments.setFont(new Font("Arial", Font.PLAIN, 18));

        board = new JPanel();
        board.setBounds(100, 100, 800, 800);
        board.setBackground(Color.LIGHT_GRAY);

        commentsBg = new JPanel();
        commentsBg.setBounds(1050,100,400,800);
        commentsBg.setBackground(Color.LIGHT_GRAY);

        add(commentsBg);
        add(board);
        add(nextTurn);
        add(saveGame);
        commentsBg.add(comments);
        setSize(1500,1200);
        setLayout(null);
        setVisible(true);
        setTitle(" Wirtualny swiat | Bartosz Bartczak 188848");

        nextTurn.addActionListener(e -> {
            loadNextTurn();
        });

        saveGame.addActionListener(e -> {
            swiat.zapiszGre();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadNextTurn(){
        swiat.wykonajTure();
        comments.setText("<html>");
        for (String comment : swiat.komentarze){
            comments.setText(comments.getText() + "<br/>" + comment);
        }
        comments.setText(comments.getText() + "</html>");

        drawBoard();
    }

    private void drawBoard(){
        int boxHeight = 800/swiat.getWysokosc();
        int boxWidth = 800/swiat.getSzerokosc();


    }
}
