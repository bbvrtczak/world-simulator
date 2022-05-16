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
    private JLabel turnCounter;
    private JLabel commentsLabel;
    private JLabel abilityLabel;
    private JLabel boardLabel;
    private JPanel commentsBg;
    private Swiat swiat;
    private Vector<JButton> places;
    private Vector<String> names;
    private JPanel board;
    private int boxWidth;
    private int boxHeight;

    Game(Swiat s){
        swiat = s;

        boxHeight = 800/(swiat.getWysokosc() + 1);
        boxWidth = 800/(swiat.getSzerokosc() + 1);

        game = new JFrame();

        game.setFocusable(true);
        game.addKeyListener(new KeyEvent(swiat, this));

        places = new Vector<>();
        names = new Vector<>();
        fillNames();

        showUI();
    }

    private void showUI(){
        JButton nextTurn = new JButton("Nastepna tura");
        nextTurn.setBounds(1100,950,300,150);
        nextTurn.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton saveGame = new JButton("Zapisz gre");
        saveGame.setBounds(700,950,300,150);
        saveGame.setFont(new Font("Arial", Font.PLAIN, 30));

        showLabels();
        showBoardAndComments();

        game.add(nextTurn);
        game.add(saveGame);
        game.setSize(1500,1200);
        game.setLayout(null);
        game.setVisible(true);
        game.setTitle(" Wirtualny swiat | Bartosz Bartczak 188848");

        drawBoard();

        nextTurn.addActionListener(e -> {
            loadNextTurn();
            game.setFocusable(true);
        });

        saveGame.addActionListener(e -> {
            swiat.zapiszGre();
        });

        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showLabels(){
        commentsLabel = new JLabel("Dziennik");
        commentsLabel.setBounds(1150,25,1000,50);
        commentsLabel.setFont(new Font("Arial", Font.PLAIN, 48));

        abilityLabel = new JLabel("U - umiejetnosc specjalna");
        abilityLabel.setBounds(25,25,1000,50);
        abilityLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        boardLabel = new JLabel("Swiat");
        boardLabel.setBounds(450,25,1000,50);
        boardLabel.setFont(new Font("Arial", Font.PLAIN, 48));

        turnCounter = new JLabel("Tura nr " + (swiat.getNumerTury()));
        turnCounter.setBounds(125,1000,1000,50);
        turnCounter.setFont(new Font("Arial", Font.PLAIN, 48));

        game.add(turnCounter);
        game.add(boardLabel);
        game.add(commentsLabel);
        game.add(abilityLabel);
    }

    private void showBoardAndComments(){
        comments = new JLabel();
        comments.setBounds(1100,100,300,800);
        comments.setFont(new Font("Arial", Font.PLAIN, 18));

        commentsBg = new JPanel();
        commentsBg.setBounds(1050,100,400,800);
        commentsBg.setBackground(Color.LIGHT_GRAY);

        board = new JPanel();
        board.setBounds(100,100,800,800);
        board.setBackground(Color.LIGHT_GRAY);
        board.setLayout(new FlowLayout());

        game.add(commentsBg);
        game.add(board);
        commentsBg.add(comments);
    }

    public void loadNextTurn(){
        turnCounter.setText("Tura nr " + (swiat.getNumerTury() + 1));
        swiat.wykonajTure();

        comments.setText("<html>");
        for(String comment : swiat.dodaneOrgKomentarze){
            comments.setText(comments.getText() + "<br/>" + comment);
        }
        for (String comment : swiat.komentarze){
            comments.setText(comments.getText() + "<br/>" + comment);
        }
        comments.setText(comments.getText() + "</html>");

        swiat.dodaneOrgKomentarze.clear();

        drawBoard();
    }

    private void createNewOrganism(Punkt p){
        JFrame choice = new JFrame("Kreator organizmow");
        JList<String> orgNames = new JList<>(names);

        choice.add(orgNames);

        choice.setSize(200,250);

        choice.show();
        orgNames.addListSelectionListener(e -> {
            String orgName = orgNames.getSelectedValue();
            if (swiat.getOrganizmNaPozycji(p) == null) {
                Organizm org = swiat.stworzOrganizm(orgName, p, swiat);
                if (org != null) {
                    org.wylaczRuch();
                    org.setIleDoRozmnozenia(1);
                    swiat.organizmy.addElement(org);
                    swiat.dodaneOrgKomentarze.addElement("Dodano " + org.organizmToString());
                    loadNextTurn();
                }
            }
            choice.dispose();
        });
    }

    private void drawBoard(){
        places.clear();
        board.removeAll();

        for (int h = 0; h < swiat.getWysokosc(); h++){
            for (int w = 0; w < swiat.getSzerokosc(); w++){
                Punkt pole = new Punkt(w,h);
                JButton place = new JButton();
                place.setPreferredSize(new Dimension(boxWidth, boxHeight));
                String orgString = "";
                if (swiat.getOrganizmNaPozycji(pole) != null) {
                    orgString = swiat.getOrganizmNaPozycji(pole).organizmToString();
                    place.add(getIcon(orgString));
                }
                JLabel labb = new JLabel(w+" "+h);
                place.add(labb);
                places.addElement(place);
            }
        }

        for (int h = 0; h < swiat.getWysokosc(); h++) {
            for (int w = 0; w < swiat.getSzerokosc(); w++) {
                board.add(places.get(h*swiat.getSzerokosc() + w));
            }
        }

        for (JButton place : places){
            place.addActionListener(e -> {
                for (int i=0;i<swiat.getWysokosc();i++){
                    for (int j=0;j<swiat.getSzerokosc();j++){
                        if (Objects.equals(place, places.get(i*swiat.getSzerokosc() + j))){
                            Punkt p = new Punkt(j,i);
                            createNewOrganism(p);
                        }
                    }
                }
            });
        }

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

    private void fillNames(){
        names.addElement("wilk");
        names.addElement("owca");
        names.addElement("lis");
        names.addElement("zolw");
        names.addElement("antylopa");
        names.addElement("trawa");
        names.addElement("mlecz");
        names.addElement("guarana");
        names.addElement("wilcze_jagody");
        names.addElement("barszcz_sosnowskiego");
    }
}