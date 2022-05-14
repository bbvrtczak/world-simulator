public class Lis extends Zwierze{
    @Override
    public void akcja() {
        int iloscProb = 0;
        Punkt nowePole;
        do{
            if (iloscProb > 20){
                return;
            }
            nowePole = swiat.losujSasiedniePole(pozycja);
            iloscProb++;
        }while(swiat.getOrganizmNaPozycji(nowePole) != null && swiat.getOrganizmNaPozycji(nowePole).getSila() > sila);
        ruch(nowePole);
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm org) {

    }

    @Override
    public String organizmToString() {
        return "lis";
    }

    Lis(Swiat s, Punkt p){
        sila = 3;
        inicjatywa = 7;
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
