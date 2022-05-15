import java.util.Random;

public class Trawa extends Roslina {
    public Trawa(Organizm other) {
        super(other);
    }

    @Override
    public void akcja() {
        int wspolczynnikRozsiania = 5;

        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa < wspolczynnikRozsiania){
            rozsianie();
        }
    }

    @Override
    public void rysowanie() {
        System.out.print("T ");
    }

    @Override
    public void specyfikaKolizji(Organizm org) {

    }

    @Override
    public String organizmToString(){
        return "trawa";
    }

    Trawa(Swiat s, Punkt p){
        sila = 0;
        wiek = 0;
        swiat = s;
        pozycja = p;
        zyje = true;
    }

    public String show(){
        return "T ";
    }
}
