import java.util.Random;

public class Guarana extends Roslina{
    @Override
    public void akcja() {
        int wspolczynnikRozsiania = 5; //TODO: zmienic

        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa < wspolczynnikRozsiania){
            rozsianie();
        }
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm zwierze) {
        zwierze.setSila(zwierze.getSila() + 3);
    }

    @Override
    public String organizmToString() {
        return "guarana";
    }

    Guarana(Swiat s, Punkt p){
        sila = 99;
        wiek = 0;
        swiat = s;
        pozycja = p;
        zyje = true;
    }
}
