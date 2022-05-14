import java.util.Random;

public class BarszczSosnowskiego extends Roslina{
    @Override
    public void akcja() {
        int wspolczynnikRozsiania = 5; //TODO: zmienic

        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa < wspolczynnikRozsiania){
            rozsianie();
        }

        Punkt p = new Punkt();
        for (int y = getPosY() - 1; y < getPosY(); y++){
            for (int x = getPosX() - 1; x < getPosX(); x++) {
                if (!(y < 0 || y > swiat.getWysokosc() || x < 0 || x > swiat.getSzerokosc())) {
                    p.setY(y);
                    p.setX(x);
                    Organizm org = swiat.getOrganizmNaPozycji(p);
                    if (org instanceof Roslina) {
                        swiat.dodajKomentarz("barszcz sosnowskiego zabil " + org.organizmToString());
                        org.zabij();
                    }
                }
            }
        }
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm org) {

    }

    @Override
    public String organizmToString() {
        return "barszcz_sosnowskiego";
    }

    BarszczSosnowskiego(Swiat s, Punkt p){
        sila = 10;
        wiek = 0;
        swiat = s;
        pozycja = p;
        zyje = true;
    }
}
