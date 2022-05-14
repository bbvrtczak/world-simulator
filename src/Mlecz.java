import java.util.Random;

public class Mlecz extends Roslina{
    @Override
    public void akcja() {
        int wspolczynnikRozsiania = 5; //TODO: zmienic

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            int szansa = rand.nextInt(100);
            if (szansa < wspolczynnikRozsiania) {
                rozsianie();
            }
        }
    }

    @Override
    public void rysowanie() {
        System.out.print("M ");
    }

    @Override
    public void specyfikaKolizji(Organizm org) {

    }

    @Override
    public String organizmToString() {
        return "mlecz";
    }

    Mlecz(Swiat s, Punkt p){
        sila = 0;
        wiek = 0;
        swiat = s;
        pozycja = p;
        zyje = true;
    }
}
