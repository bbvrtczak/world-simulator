public class Owca extends Zwierze{
    @Override
    public void akcja() {
        Punkt nowePole = swiat.losujSasiedniePole(pozycja);
        ruch(nowePole);
    }

    @Override
    public void rysowanie() {

    }

    @Override
    public void specyfikaKolizji(Organizm org) {
        System.out.print("O ");
    }

    @Override
    public String organizmToString() {
        return "owca";
    }

    public Owca(Swiat s, Punkt p){
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
        return "O ";
    }
}
