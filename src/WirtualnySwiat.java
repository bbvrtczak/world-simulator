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

        swiat.zapiszGre();

        for(int i=0; i < 100; i++){
           swiat.wykonajTure();
        }

    }
}
