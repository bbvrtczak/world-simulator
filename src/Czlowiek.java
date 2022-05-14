public class Czlowiek extends Zwierze{

    protected int umiejetnoscIleTur;
    protected int umiejetnoscCooldown;

    public Czlowiek(Swiat s, Punkt p){
        sila = 5;
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
        rozum = true;
        umiejetnoscCooldown = 0;
        umiejetnoscIleTur = 0;
    }

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
        if (swiat.czyCzlowiekZyje && czyUzywaUmiejetnosci()) {
            Zwierze atakujaceZwierze = null;
            atakujaceZwierze = (Zwierze) atakujacy;
            atakujaceZwierze.ruch(swiat.losujWolneSasiedniePole(pozycja));
            atakujaceZwierze.wylaczRuch();
            swiat.dodajKomentarz("czlowiek uniknal ataku " + atakujacy.organizmToString());
        }
    }

    @Override
    public String organizmToString() {
        return "czlowiek";
    }

    public boolean czyMozeUzycUmiejetnosci(){
        return umiejetnoscCooldown == 0;
    }

    public boolean czyUzywaUmiejetnosci(){
        return umiejetnoscIleTur > 0;
    }

    public void uzyjUmiejetnosc(){
        umiejetnoscIleTur = 5;
        umiejetnoscCooldown = 10;
        niesmiertelnosc = 5;
    }

    public void zmniejszCooldown(){
        if (umiejetnoscCooldown > 0){
            umiejetnoscCooldown--;
        }
        if (umiejetnoscIleTur > 0){
            umiejetnoscIleTur--;
        }
    }

    public int getIloscTur(){
        return umiejetnoscIleTur;
    }

    public int getUmiejetnoscCooldown(){
        return umiejetnoscCooldown;
    }
    public void setTuryUmiejetnosci(int t){
        umiejetnoscIleTur = t;
    }

    public void setCooldownUmiejetnosci(int c){
        umiejetnoscCooldown = c;
    }
}
