import java.util.Random;
import java.util.Vector;

public class Swiat {

    private static final int NIE_PORUSZA_SIE = 0;

    protected int wysokosc;
    protected int szerokosc;
    protected int numerTury;
    protected boolean czyCzlowiekZyje;
    protected int kierunekCzlowieka;
    protected Vector<Organizm> organizmy;
    protected Vector<Organizm> dodaneOrganizmy;
    protected Vector<String> komentarze;

    public void wykonajTure(){
        dodaneOrganizmy.clear();
        komentarze.clear();




        for(Organizm org : organizmy){
            org.dodajWiek();
        }
    }

    public void przygotujSwiat(Swiat swiat){
        //dodawanie organizmow
    }

    public Swiat getSwiat(){
        return this;
    }

    public Swiat(int wysokosc, int szerokosc){
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        numerTury = 0;
        czyCzlowiekZyje = true;
        kierunekCzlowieka = NIE_PORUSZA_SIE;
    }

    public Organizm getOrganizmNaPozycji(Punkt p){
        for(Organizm org : organizmy){
            if(org.getPozycja() == p)
                return org;
        }
        return null;
    }

    public Punkt losujWolnePole(){
        Punkt pole = new Punkt();
        Random rand = new Random();
        do{
            pole.setX(rand.nextInt(szerokosc));
            pole.setY(rand.nextInt(wysokosc));
        }
        while(getOrganizmNaPozycji(pole) != null);

        return pole;
    }

    public Punkt losujSasiedniePole(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<Punkt>();
        Punkt pole = new Punkt(-1,-1);
        for(int y = p.getX() - 1; y < p.getY() + 2; y++){
            for (int x = p.getX() - 1; x < p.getX() + 2; x++){
                if (y < 0 || y > wysokosc || x < 0 || x > szerokosc) continue;
                else{
                    pole.setX(x);
                    pole.setY(y);
                    sasiedniePola.add(pole);
                }
            }
        }
        Random rand = new Random();
        if (sasiedniePola.size() > 0)
            return sasiedniePola.get(rand.nextInt(sasiedniePola.size()));
        else
            return pole;
    }

    public Punkt losujSasiedniePoleR2(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<Punkt>();
        Punkt pole = new Punkt(-1,-1);
        for(int y = p.getX() - 2; y < p.getY() + 3; y++){
            for (int x = p.getX() - 2; x < p.getX() + 3; x++){
                if (y < 0 || y > wysokosc || x < 0 || x > szerokosc);
                else{
                    pole.setX(x);
                    pole.setY(y);
                    sasiedniePola.add(pole);
                }
            }
        }
        Random rand = new Random();
        if (sasiedniePola.size() > 0)
            return sasiedniePola.get(rand.nextInt(sasiedniePola.size()));
        else
            return pole;
    }

    public void wyczyscKomentarze(){
        komentarze.clear();
    }

    public void dodajOrganizm(Organizm org){
        organizmy.add(org);
    }

    public void dodajOrganizmTymczasowy(Organizm org){
        dodaneOrganizmy.add(org);
    }

    public void dodajKomentarz(String kom){
        komentarze.add(kom);
    }

    public Vector<Organizm> getDodaneOrganizmy(){
        return dodaneOrganizmy;
    }

    /*public int getIndexOrganizmu(Organizm szukany){
        int index = 0;
        for (Organizm org : organizmy){
            if (szukany == org)
                return index;
            index++;
        }
        return -1;
    }*/

    public void usunOrganizm(Organizm org){
        //int index = getIndexOrganizmu(org);
        //if (index != -1)
            organizmy.remove(org);
    }

    public void usunMartweOrganizmy(){
        for(Organizm org : organizmy){
            if(!org.czyZyje())
                usunOrganizm(org);
        }
    }

    public boolean czyJestWolnePole(){
        return organizmy.size() + dodaneOrganizmy.size() < wysokosc*szerokosc;
    }

    public int getWysokosc(){
        return wysokosc;
    }

    public int getSzerokosc(){
        return szerokosc;
    }

    public Punkt getPozycjaCzlowieka(){
        for(Organizm org : organizmy){
            if (org instanceof Trawa) //TODO: zmienic na czlowieka
                return org.getPozycja();
        }
        return new Punkt(-1,-1);
    }

    public void zabijCzlowieka(){
        czyCzlowiekZyje = false;
    }

    public int wKtoraStroneRuszySieCzlowiek(){
        return kierunekCzlowieka;
    }

    public boolean getCzyCzlowiekZyje(){
        return czyCzlowiekZyje;
    }

    public Punkt losujWolneSasiedniePole(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<Punkt>();
        for (int y = p.getY() - 1; y < p.getY() + 2; y++) {
            for (int x = p.getX() - 1; x < p.getX() + 2; x++) {
                if (y < 0 || y > this.wysokosc - 1 || x < 0 || x > this.szerokosc - 1){
                    ;
                }
                else {
                    Punkt ptmp = new Punkt(x, y);
                    if (this.getOrganizmNaPozycji(ptmp) == null)
                        sasiedniePola.addElement(ptmp);
                }
            }
        }

        int iloscPol = sasiedniePola.size();
        if (iloscPol == 0)
            return new Punkt(-1,-1);
        Random rand = new Random();
        return sasiedniePola.get(rand.nextInt(iloscPol));
    }

    public Organizm stworzOrganizm(String orgString, Punkt p, Swiat swiat){
        Organizm org = null;
        if(orgString == "trawa"){
            Trawa trawa = new Trawa(swiat, p);
            org = trawa;
        }
        return org;
    }
}
