import java.util.*;

public class Swiat {

    private static final int NIE_PORUSZA_SIE = 0;
    private static final int GORA = 1;
    private static final int DOL = 2;
    private static final int LEWO = 3;
    private static final int PRAWO = 4;

    protected int wysokosc;
    protected int szerokosc;
    protected int numerTury;
    protected boolean czyCzlowiekZyje;
    protected int kierunekCzlowieka;
    protected Vector<Organizm> organizmy;
    protected Vector<Organizm> dodaneOrganizmy;
    protected Vector<Organizm> zabiteOrganizmy;
    protected Vector<String> komentarze;

    public void wykonajTure(){
        dodaneOrganizmy.clear();
        komentarze.clear();

        if (czyCzlowiekZyje) {
            Czlowiek czl;
            Organizm organizmCzlowieka = getOrganizmNaPozycji(getPozycjaCzlowieka());  //getter czlowieka
            czl = (Czlowiek) organizmCzlowieka;
            if (czl.czyUzywaUmiejetnosci()) {
                dodajKomentarz("czlowiek uzywa umiejetnosci - pozostalo " + czl.getIloscTur() + " tur");
                czl.zmniejszCooldown();
            } else if (czl.getUmiejetnoscCooldown() > 0) {
                czl.zmniejszCooldown();
            }
        }
        if (!czyCzlowiekZyje){
            dodajKomentarz("czlowiek nie zyje - nie mozna sie ruszyc");
        }

        sortujOrganizmy();

        for (Organizm org : organizmy){
            if (org.czyZyje()) {
                org.akcja();
            }
        }

        usunMartweOrganizmy();
        zabiteOrganizmy.clear();

        for(Organizm org : organizmy){
            org.dodajWiek();
            if (org.getIleDoRozmnozenia() > 0){
                org.zmniejszCooldownRozmnozenia();
            }
            org.zmniejszNiesmiertelnosc();
        }

        uaktualnijOrganizmy();
        numerTury++;
    }

    public void uaktualnijOrganizmy(){
        for(Organizm org : dodaneOrganizmy){
            organizmy.addElement(org);
        }
    }

    public void przygotujSwiat(Swiat swiat){
        //dodawanie organizmow
        organizmy = new Vector<>();
        dodaneOrganizmy = new Vector<>();
        zabiteOrganizmy = new Vector<>();
        komentarze = new Vector<>();
        //organizmy.addElement(stworzOrganizm("czlowiek", losujWolnePole(), swiat));
        //organizmy.addElement(stworzOrganizm("trawa", losujWolnePole(), swiat));
        //organizmy.addElement(stworzOrganizm("mlecz", losujWolnePole(), swiat));
        organizmy.addElement(stworzOrganizm("guarana", losujWolnePole(), swiat));
        //organizmy.addElement(stworzOrganizm("wilcze_jagody", losujWolnePole(), swiat));
        //organizmy.addElement(stworzOrganizm("barszcz_sosnowskiego", losujWolnePole(), swiat));
        organizmy.addElement(stworzOrganizm("wilk", losujWolnePole(), swiat));
    }

    public Swiat getSwiat(){
        return this;
    }

    public Swiat(int wysokosc, int szerokosc){
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;
        numerTury = 0;
        czyCzlowiekZyje = false;
        kierunekCzlowieka = NIE_PORUSZA_SIE;
    }

    public Organizm getOrganizmNaPozycji(Punkt p){
        for(Organizm org : organizmy){
            if(Objects.equals(org.getPozycja(), p))
                return org;
        }
        return null;
    }

    public Punkt losujWolnePole(){
        Punkt pole = new Punkt(-1,-1);
        Random rand = new Random();
        do{
            pole.setX(rand.nextInt(szerokosc - 1));
            pole.setY(rand.nextInt(wysokosc - 1));
        }
        while(getOrganizmNaPozycji(pole) != null);

        return pole;
    }

    public Punkt losujSasiedniePole(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<>();
        Punkt zajete = new Punkt(-1,-1);
        for(int y = p.getX() - 1; y < p.getY() + 2; y++){
            for (int x = p.getX() - 1; x < p.getX() + 2; x++){
                if (!(y < 0 || y >= wysokosc || x < 0 || x >= szerokosc || (y == p.getY() && x == p.getX()))){
                    Punkt pole = new Punkt(x,y);
                    sasiedniePola.add(pole);
                }
            }
        }

        Random rand = new Random();
        if (!sasiedniePola.isEmpty())
            return sasiedniePola.get(rand.nextInt(sasiedniePola.size()));
        else
            return zajete;
    }

    public Punkt losujSasiedniePoleR2(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<Punkt>();
        Punkt zajete = new Punkt(-1,-1);
        for(int y = p.getX() - 2; y < p.getY() + 3; y++){
            for (int x = p.getX() - 2; x < p.getX() + 3; x++){
                if (!(y < 0 || y >= wysokosc || x < 0 || x >= szerokosc)){
                    Punkt pole = new Punkt(x,y);
                    sasiedniePola.add(pole);
                }
            }
        }
        Random rand = new Random();
        if (!sasiedniePola.isEmpty())
            return sasiedniePola.get(rand.nextInt(sasiedniePola.size()));
        else
            return zajete;
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
        for(Organizm org : zabiteOrganizmy){
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
            if (org instanceof Czlowiek)
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
        Vector<Punkt> sasiedniePola = new Vector<>();
        for (int y = p.getY() - 1; y < p.getY() + 2; y++) {
            for (int x = p.getX() - 1; x < p.getX() + 2; x++) {
                if (!(y < 0 || y > this.wysokosc - 1 || x < 0 || x > this.szerokosc - 1 || (y == p.getY() && x == p.getX()))){
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
        if (organizmy.size() < wysokosc*szerokosc) {
            if (Objects.equals(orgString, "czlowiek")) {
                Czlowiek org = new Czlowiek(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "trawa")) {
                Trawa org = new Trawa(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "mlecz")) {
                Mlecz org = new Mlecz(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "guarana")) {
                Guarana org = new Guarana(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "wilcze_jagody")) {
                WilczeJagody org = new WilczeJagody(swiat, p);
                return org;
           }
            else if (Objects.equals(orgString, "barszcz_sosnowskiego")) {
                BarszczSosnowskiego org = new BarszczSosnowskiego(swiat, p);
                return org;
           }
            else if (Objects.equals(orgString, "wilk")) {
                Wilk org = new Wilk(swiat, p);
                return org;
            }
        }
        return null;
    }

    public int czyCzlowiekSieRusza() {
        return kierunekCzlowieka;
    }

    public Punkt getNowePoleCzlowieka() {
        Punkt obecnaPozycja = getPozycjaCzlowieka();
        Punkt nowePole = new Punkt();
        nowePole = obecnaPozycja;
        switch (kierunekCzlowieka){ //TODO: zagmatwane kierunki
            case GORA:
                if (obecnaPozycja.getX() > 0){
                    nowePole.setX(obecnaPozycja.getX() - 1);
                }
                break;
            case DOL:
                if (obecnaPozycja.getX() < getWysokosc() - 1){
                    nowePole.setX(obecnaPozycja.getX() + 1);
                }
                break;
            case LEWO:
                if (obecnaPozycja.getY() > 0){
                    nowePole.setY(obecnaPozycja.getY() + 1);
                }
                break;
            case PRAWO:
                if (obecnaPozycja.getY() < getSzerokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() + 1);
                }
                break;
        }
        kierunekCzlowieka = NIE_PORUSZA_SIE;
        return nowePole;
    }

    public void sortujOrganizmy() {
        organizmy.sort((o1, o2) -> {
            if (o1.getInicjatywa() != o2.getInicjatywa())
                return Integer.compare(o2.getInicjatywa(), o1.getInicjatywa());
            else
                return Integer.compare(o1.getWiek(), o2.getWiek());
        });
    }
}
