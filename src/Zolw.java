public class Zolw extends Zwierze{
    @Override
    public void akcja() {
        Punkt nowePole = swiat.losujSasiedniePole(pozycja);
        ruch(nowePole);
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm atakujacy) {
        if (atakujacy.getSila() < 5){
            niesmiertelnosc = 1;
            odbilAtak = true;
            atakujacy.wylaczRuch();
            swiat.dodajKomentarz("zolw odbil atak " + atakujacy.organizmToString());
        }
    }

    @Override
    public String organizmToString() {
        return "zolw";
    }

    Zolw(Swiat s, Punkt p){
        sila = 2;
        inicjatywa = 1;
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
}
