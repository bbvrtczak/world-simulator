public abstract class Organizm {


    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected Punkt pozycja;
    protected boolean zyje;
    protected boolean czySieRozmnozyl;
    protected int ileDoRozmnozenia;
    protected int niesmiertelnosc;
    protected boolean odbilAtak;
    protected boolean czyMozeSieRuszyc;
    protected boolean rozum; //TODO: czy potrzebne???
    protected Swiat swiat;

    public abstract void akcja();

    public abstract void kolizja(Organizm org);

    public abstract void rysowanie();

    public abstract void specyfikaKolizji(Organizm org); //TODO: sprobowac zrobic w klasycznej kolizji

    public Punkt getPozycja(){
        return pozycja;
    }

    public int getPosX(){
        return pozycja.getX();
    }

    public int getPosY(){
        return pozycja.getY();
    }

    public int getWiek(){
        return wiek;
    }

    public int getInicjatywa(){
        return inicjatywa;
    }

    public int getSila(){
        return sila;
    }

    public void setPozycja(Punkt p){
        this.pozycja = p;
    }

    public void setSila(int sila){
        this.sila = sila;
    }

    public void setInicjatywa(int inicjatywa){
        this.inicjatywa = inicjatywa;
    }

    public void setWiek(int wiek){
        this.wiek = wiek;
    }

    public void dodajWiek(){
        wiek++;
    }

    public boolean czyZyje(){
        return zyje;
    }

    public void zabij(){
        zyje = false;
        swiat.zabiteOrganizmy.addElement(this);
        if (this instanceof Czlowiek){
            swiat.czyCzlowiekZyje = false;
        }
    }

    public void rozmnozylSie(){
        ileDoRozmnozenia = 10;
        czySieRozmnozyl = true;
    }

    public int getIleDoRozmnozenia(){
        return ileDoRozmnozenia;
    }

    public boolean getCzySieRozmnozyl(){
        return czySieRozmnozyl;
    }

    public void setIleDoRozmnozenia(int rozmnozenie){
        ileDoRozmnozenia = rozmnozenie;
    }

    public void zmniejszCooldownRozmnozenia(){
        if(ileDoRozmnozenia > 0)
            ileDoRozmnozenia--;
    }

    public boolean czyNiesmiertelny(){
        return niesmiertelnosc > 0;
    }

    public boolean czyOdbilAtak(){
        return odbilAtak;
    }

    public boolean czyZdolnyDoRuchu(){
        return czyMozeSieRuszyc;
    }

    public void wylaczRuch(){
        czyMozeSieRuszyc = false;
    }

    public void wlaczRuch(){
        czyMozeSieRuszyc = true;
    }

    public boolean czySieRuszy(){
        if(!czyMozeSieRuszyc){
            czyMozeSieRuszyc = true;
            return false;
        }
        return true;
    }

    public Swiat getSwiat(){
        return swiat;
    }

    public abstract String organizmToString();

    public void zmniejszNiesmiertelnosc() {
        if (niesmiertelnosc > 0)
            niesmiertelnosc--;
    }
}