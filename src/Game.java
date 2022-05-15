import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class Game {

    private JFrame game;

    private JLabel comments;
    private JPanel commentsBg;
    Swiat swiat;
    private Vector<JPanel> places;
    private JPanel board;

    private int boxWidth;
    private int boxHeight;

    Game(Swiat s){
        swiat = s;

        game = new JFrame();

        places = new Vector<>();

        boxHeight = 800/(swiat.getWysokosc() + 1);
        boxWidth = 800/(swiat.getSzerokosc() + 1);

        showUI();
    }

    private JLabel getIcon(String org){
        try {
            BufferedImage icon;
            JLabel pic;

            icon = ImageIO.read(new File("nothing.png"));

            if (Objects.equals(org,"wilk")){
                icon = ImageIO.read(new File("wolf_icon.png"));
            }
            else if (Objects.equals(org,"owca")){
                icon = ImageIO.read(new File("sheep_icon.png"));
            }
            else if (Objects.equals(org,"antylopa")){
                icon = ImageIO.read(new File("antelope_icon.png"));
            }
            else if (Objects.equals(org,"lis")){
                icon = ImageIO.read(new File("fox_icon.png"));
            }
            else if (Objects.equals(org,"zolw")){
                icon = ImageIO.read(new File("turtle_icon.png"));
            }
            else if (Objects.equals(org,"trawa")){
                icon = ImageIO.read(new File("grass_icon.png"));
            }
            else if (Objects.equals(org,"czlowiek")){
                icon = ImageIO.read(new File("human_icon.jpg"));
            }
            else if (Objects.equals(org,"guarana")){
                icon = ImageIO.read(new File("guarana_icon.png"));
            }
            else if (Objects.equals(org,"mlecz")){
                icon = ImageIO.read(new File("dandelion_icon.png"));
            }
            else if (Objects.equals(org,"wilcze_jagody")){
                icon = ImageIO.read(new File("berries_icon.png"));
            }
            else if (Objects.equals(org,"barszcz_sosnowskiego")){
                icon = ImageIO.read(new File("borscht_icon.png"));
            }

            Image img = icon.getScaledInstance(boxWidth, boxHeight, Image.SCALE_DEFAULT);
            pic = new JLabel(new ImageIcon(img));

            return pic;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showUI(){
        JButton nextTurn = new JButton("Nastepna tura");
        nextTurn.setBounds(1100,950,300,150);
        nextTurn.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton saveGame = new JButton("Zapisz gre");
        saveGame.setBounds(700,950,300,150);
        saveGame.setFont(new Font("Arial", Font.PLAIN, 30));

        comments = new JLabel();
        comments.setBounds(1100,100,300,800);
        comments.setFont(new Font("Arial", Font.PLAIN, 18));

        commentsBg = new JPanel();
        commentsBg.setBounds(1050,100,400,800);
        commentsBg.setBackground(Color.LIGHT_GRAY);

        board = new JPanel();
        board.setBounds(100,100,800,800);
        board.setBackground(Color.LIGHT_GRAY);
        //board.add(wolfPic);
        board.setLayout(new FlowLayout());

        game.add(commentsBg);
        game.add(board);
        game.add(nextTurn);
        game.add(saveGame);
        commentsBg.add(comments);
        game.setSize(1500,1200);
        game.setLayout(null);
        game.setVisible(true);
        game.setTitle(" Wirtualny swiat | Bartosz Bartczak 188848");

        swiat.wykonajTure(); //TODO: czy juz wykonywac?

        nextTurn.addActionListener(e -> {
            loadNextTurn();
        });

        saveGame.addActionListener(e -> {
            swiat.zapiszGre();
        });

        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        places.clear();
        board.removeAll();

        for (int h = 0; h < swiat.getWysokosc(); h++){
            for (int w = 0; w < swiat.getSzerokosc(); w++){
                Punkt pole = new Punkt(w,h);
                JPanel place = new JPanel();
                place.setPreferredSize(new Dimension(boxWidth, boxHeight));
                String orgString = "";
                if (swiat.getOrganizmNaPozycji(pole) != null) {
                    orgString = swiat.getOrganizmNaPozycji(pole).organizmToString();
                    place.add(getIcon(orgString));
                }
                places.addElement(place);
            }
        }

        for (int h = 0; h < swiat.getWysokosc(); h++) {
            for (int w = 0; w < swiat.getSzerokosc(); w++) {
                board.add(places.get(h*swiat.getSzerokosc() + w));
            }
        }

    }
}
