import java.util.Random;

public class Antylopa extends Zwierze{
    @Override
    public void akcja() {
        Punkt nowePole = swiat.losujSasiedniePoleR2(pozycja);
        ruch(nowePole);
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm atakujacy) {
        int szansaNaUnik = 50;
        Random rand = new Random();
        int szansa = rand.nextInt(100);
        if (szansa < szansaNaUnik){
            niesmiertelnosc = 1;
            odbilAtak = true;
            swiat.dodajKomentarz("antylopa uniknela ataku " + atakujacy.organizmToString());
            Punkt nowePole = swiat.losujWolneSasiedniePole(pozycja);
            ruch(nowePole);
            atakujacy.wylaczRuch();
        }
    }

    @Override
    public String organizmToString() {
        return "antylopa";
    }

    Antylopa(Swiat s, Punkt p){
        sila = 4;
        inicjatywa = 4;
        wiek = 0;
        swiat = s;
        pozycja = p;
        zyje = true;
        czySieRozmnozyl = false;
        ileDoRozmnozenia = 0;
        niesmiertelnosc = 0;
        odbilAtak = false;
        czyMozeSieRuszyc = true;
        rozum = false;
    }

    public String show(){
        return "A ";
    }

}
