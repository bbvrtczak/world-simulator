import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class WirtualnySwiat {

    public static void main(String[] args){
        int x, y;

        Scanner scanner = new Scanner(System.in);

        x = scanner.nextInt();
        y = scanner.nextInt();



        Swiat swiat = new Swiat(x,y);

        swiat.przygotujSwiat(swiat);




        //Swiat swiat = new Swiat(y,x);

        /*JFrame f=new JFrame("Wirtualny swiat | Bartosz Bartczak 188848");//creating instance of JFrame

        JButton b=new JButton("click");//creating instance of JButton

        JTextField fieldWysokosc = new JTextField(20);
        JTextField fieldSzerokosc = new JTextField(20);

        b.setBounds(260,200,300, 120);//x axis, y axis, width, height
        fieldSzerokosc.setBounds(260,400,300, 120);//x axis, y axis, width, height
        fieldWysokosc.setBounds(260,600,300, 120);//x axis, y axis, width, height
        b.setFont(new Font("Arial", Font.PLAIN, 40));

        f.add(b);//adding button in JFrame
        f.add(fieldSzerokosc);
        f.add(fieldWysokosc);

        f.setSize(800,1000);//400 width and 500 height
        //f.setLayout(null);//using no layout managers
        //f.setVisible(true);//making the frame visible
        f.show();*/

        // JTextField
        JTextField t;

        // JFrame
         JFrame f;

        // JButton
         JButton b;

        // label to display text
         JLabel l;

        f = new JFrame("textfield");

        // create a new button
        b = new JButton("submit");

        // create a object of JTextField with 16 columns and a given initial text
        t = new JTextField("enter the text", 16);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(t);
        p.add(b);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(800, 1000);

        f.show();

    }
}
