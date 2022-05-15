import java.util.Random;

public class Wilk extends Zwierze{
    @Override
    public void akcja() {
        Punkt nowePole = swiat.losujSasiedniePole(pozycja);
        ruch(nowePole);
    }

    @Override
    public void rysowanie() {
        System.out.print("W ");
    }

    @Override
    public void specyfikaKolizji(Organizm org) {

    }

    @Override
    public String organizmToString() {
        return "wilk";
    }

    Wilk(Swiat s, Punkt p){
        sila = 9;
        inicjatywa = 5;
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
        return "W ";
    }
}
