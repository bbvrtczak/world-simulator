import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Swiat {

    private static final int NIE_PORUSZA_SIE = 0;
    private static final int GORA = 1;
    private static final int DOL = 2;
    private static final int LEWO = 3;
    private static final int PRAWO = 4;

    private static final int UP_LEFT = 5;
    private static final int UP_RIGHT = 6;
    private static final int DOWN_LEFT = 7;
    private static final int DOWN_RIGHT = 8;

    protected int wysokosc;
    protected int szerokosc;
    protected int numerTury;
    protected boolean czyCzlowiekZyje;
    protected int kierunekCzlowieka;
    protected Vector<Organizm> organizmy;
    protected Vector<Organizm> dodaneOrganizmy;
    protected Vector<Organizm> zabiteOrganizmy;
    protected Vector<String> komentarze;
    protected Vector<String> dodaneOrgKomentarze;
    protected boolean isHex;

    public void rysujSwiat(){
        System.out.println("----------------------------");
        for (int h=0;h<wysokosc;h++){
            for (int w=0;w<szerokosc;w++){
                Punkt p = new Punkt(w,h);
                Organizm org = getOrganizmNaPozycji(p);
                if (org != null)
                    org.rysowanie();
                else
                    System.out.print("  ");
                System.out.print(" | ");
            }
            System.out.println();
            System.out.println("--------------------");
        }
        System.out.println("----------------------------");
    }

    public void wykonajTure(){
        //rysujSwiat();
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
            if (czl.getUmiejetnoscCooldown() > 0 && czl.getUmiejetnoscCooldown() < 5){
                dodajKomentarz("Umiejetnosc specjalna sie odnawia - pozostalo " + czl.getUmiejetnoscCooldown() + " tur");
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

    public void przygotujSwiat(Swiat swiat, int hex){
        //dodawanie organizmow
        organizmy = new Vector<>();
        dodaneOrganizmy = new Vector<>();
        zabiteOrganizmy = new Vector<>();
        komentarze = new Vector<>();
        dodaneOrgKomentarze = new Vector<>();

        if (hex == 1){
            isHex = true;
        }
        else{
            isHex = false;
        }

        organizmy.addElement(stworzOrganizm("czlowiek", losujWolnePole(), swiat));
        for (int i = 0; i < 2; i++) {
            organizmy.addElement(stworzOrganizm("trawa", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("mlecz", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("guarana", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("wilcze_jagody", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("barszcz_sosnowskiego", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("wilk", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("owca", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("lis", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("zolw", losujWolnePole(), swiat));
            organizmy.addElement(stworzOrganizm("antylopa", losujWolnePole(), swiat));
        }
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
        Punkt zajete = new Punkt(-1,-1);
        Random rand = new Random();
        if (isHex){
            Vector<Punkt> polaHex = getSasiedniePolaHex(p);
            if (!polaHex.isEmpty())
                return polaHex.get(rand.nextInt(polaHex.size()));
            return zajete;
        }
        Vector<Punkt> sasiedniePola = new Vector<>();
        for(int y = p.getX() - 1; y < p.getY() + 2; y++){
            for (int x = p.getX() - 1; x < p.getX() + 2; x++){
                if (!(y < 0 || y >= wysokosc || x < 0 || x >= szerokosc || (y == p.getY() && x == p.getX()))){
                    Punkt pole = new Punkt(x,y);
                    sasiedniePola.add(pole);
                }
            }
        }

        if (!sasiedniePola.isEmpty())
            return sasiedniePola.get(rand.nextInt(sasiedniePola.size()));
        else
            return zajete;
    }

    public Punkt losujSasiedniePoleR2(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<>();
        Punkt zajete = new Punkt(-1,-1);
        for(int y = p.getX() - 2; y < p.getY() + 3; y++){
            for (int x = p.getX() - 2; x < p.getX() + 3; x++){
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

    public Punkt losujWolneSasiedniePole(Punkt p){
        Random rand = new Random();
        if (isHex){
            Vector<Punkt> polaHex = getWolneSasiedniePolaHex(p);
            if (!polaHex.isEmpty())
                return polaHex.get(rand.nextInt(polaHex.size()));
            return new Punkt(-1,-1);
        }
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
        return sasiedniePola.get(rand.nextInt(iloscPol));
    }

    public Vector<Punkt> getSasiedniePolaHex(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<>();
        for(int y = p.getX() - 2; y < p.getY() + 3; y++){
            for (int x = p.getX() - 2; x < p.getX() + 3; x++){
                if (!(y < 0 || y >= wysokosc || x < 0 || x >= szerokosc || (y == p.getY() && x == p.getX()))){
                    Punkt pole = new Punkt(x,y);
                    if ( (pole.getX() == p.getX() && (pole.getY() == p.getY() - 1 || pole.getY() == p.getY() +1)) ||
                        (pole.getX() == p.getX() + 1 && (pole.getY() == p.getY() - 1 || pole.getY() == p.getY() +1)) ||
                        (pole.getX() == p.getX() - 1 && pole.getY() == p.getY()) || (pole.getX() == p.getX() + 1 && pole.getY() == p.getY()) ) {
                            sasiedniePola.add(pole);
                    }
                }
            }
        }
        return sasiedniePola;
    }

    public Vector<Punkt> getWolneSasiedniePolaHex(Punkt p){
        Vector<Punkt> sasiedniePola = new Vector<>();
        for(int y = p.getX() - 2; y < p.getY() + 3; y++){
            for (int x = p.getX() - 2; x < p.getX() + 3; x++){
                if (!(y < 0 || y >= wysokosc || x < 0 || x >= szerokosc || (y == p.getY() && x == p.getX()))){
                    Punkt pole = new Punkt(x,y);
                    if ( (pole.getX() == p.getX() && (pole.getY() == p.getY() - 1 || pole.getY() == p.getY() +1)) ||
                            (pole.getX() == p.getX() + 1 && (pole.getY() == p.getY() - 1 || pole.getY() == p.getY() +1)) ||
                            (pole.getX() == p.getX() - 1 && pole.getY() == p.getY()) || (pole.getX() == p.getX() + 1 && pole.getY() == p.getY()) ) {
                        if (this.getOrganizmNaPozycji(pole) == null) {
                            sasiedniePola.add(pole);
                        }
                    }
                }
            }
        }
        return sasiedniePola;
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

    public void usunOrganizm(Organizm org){
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
                return new Punkt(org.getPozycja().getX(), org.getPozycja().getY());
        }
        return new Punkt(-1,-1);
    }

    public int wKtoraStroneRuszySieCzlowiek(){
        return kierunekCzlowieka;
    }

    public boolean getCzyCzlowiekZyje(){
        return czyCzlowiekZyje;
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
            else if (Objects.equals(orgString, "owca")) {
                Owca org = new Owca(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "lis")) {
                Lis org = new Lis(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "zolw")) {
                Zolw org = new Zolw(swiat, p);
                return org;
            }
            else if (Objects.equals(orgString, "antylopa")) {
                Antylopa org = new Antylopa(swiat, p);
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
                if (obecnaPozycja.getY() > 0){
                    nowePole.setY(obecnaPozycja.getY() - 1);
                }
                break;
            case DOL:
                if (obecnaPozycja.getY() < getWysokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() + 1);
                }
                break;
            case LEWO:
                if (obecnaPozycja.getX() > 0){
                    nowePole.setX(obecnaPozycja.getX() - 1);
                }
                break;
            case PRAWO:
                if (obecnaPozycja.getX() < getSzerokosc() - 1){
                    nowePole.setX(obecnaPozycja.getX() + 1);
                }
                break;
            case UP_LEFT:
                if (obecnaPozycja.getX() < getSzerokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() - 1);
                    if (obecnaPozycja.getY() % 2 == 1)
                        nowePole.setX(obecnaPozycja.getX() - 1);
                }
                break;
            case UP_RIGHT:
                if (obecnaPozycja.getX() < getSzerokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() - 1);
                    if (obecnaPozycja.getY() % 2 == 0)
                        nowePole.setX(obecnaPozycja.getX() + 1);
                }
                break;
            case DOWN_LEFT:
                if (obecnaPozycja.getX() < getSzerokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() + 1);
                    if (obecnaPozycja.getY() % 2 == 1)
                        nowePole.setX(obecnaPozycja.getX() - 1);
                }
                break;
            case DOWN_RIGHT:
                if (obecnaPozycja.getX() < getSzerokosc() - 1){
                    nowePole.setY(obecnaPozycja.getY() + 1);
                    if (obecnaPozycja.getY() % 2 == 0)
                        nowePole.setX(obecnaPozycja.getX() + 1);
                }
                break;
        }
        kierunekCzlowieka = NIE_PORUSZA_SIE;
        System.out.println(nowePole);
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

    public void zapiszGre(){
        FileWriter save;

        try {
            if (isHex)
                save = new FileWriter("saveHex.txt");
            else
                save = new FileWriter("save.txt");

            save.write(wysokosc + " " + szerokosc + " " + numerTury + " " + czyCzlowiekZyje + " " + isHex);
            for (Organizm org : organizmy){
                save.write("\n");
                save.write(org.organizmToString() + " " + org.getPosX() + " " + org.getPosY() + " " + org.getSila() + " " + org.getInicjatywa() + " " + org.getWiek() + " " + org.getIleDoRozmnozenia());
                if (org instanceof Czlowiek){
                    Czlowiek czl = (Czlowiek) org;
                    save.write(" " + czl.getIloscTur() + " " + czl.getUmiejetnoscCooldown());
                }
            }
            save.close();
        } catch (IOException e){
            System.out.println("There was an error saving your game");
            e.printStackTrace();
        }
    }

    public void wczytajGre(Swiat swiat, String mode) throws FileNotFoundException {
        organizmy = new Vector<>();
        dodaneOrganizmy = new Vector<>();
        zabiteOrganizmy = new Vector<>();
        komentarze = new Vector<>();
        dodaneOrgKomentarze = new Vector<>();
        File readSave;
        readSave = new File("save.txt");
        if (Objects.equals(mode, "hex"))
            readSave = new File("saveHex.txt");
        Scanner save = new Scanner(readSave);
        int x, y, sila, inicjatywa, wiek, rozmnozenie, umiejetnoscTury = 0, umiejetnoscCooldown = 0;
        String nazwaOrganizmu;
        wysokosc = save.nextInt();
        szerokosc = save.nextInt();
        numerTury = save.nextInt();
        czyCzlowiekZyje = save.nextBoolean();
        isHex = save.nextBoolean();
        while (save.hasNext()) {
            nazwaOrganizmu = save.next();
            x = save.nextInt();
            y = save.nextInt();
            sila = save.nextInt();
            inicjatywa = save.nextInt();
            wiek = save.nextInt();
            rozmnozenie = save.nextInt();
            if (Objects.equals(nazwaOrganizmu, "czlowiek")) {
                umiejetnoscTury = save.nextInt();
                umiejetnoscCooldown = save.nextInt();
            }
            Punkt pole = new Punkt(x, y);
            organizmy.addElement(stworzOrganizm(nazwaOrganizmu, pole, swiat));
            Organizm org = getOrganizmNaPozycji(pole);
            org.setSila(sila);
            org.setInicjatywa(inicjatywa);
            org.setWiek(wiek);
            org.setIleDoRozmnozenia(rozmnozenie);
            if (Objects.equals(nazwaOrganizmu, "czlowiek")) {
                Czlowiek czl = (Czlowiek) org;
                czl.setTuryUmiejetnosci(umiejetnoscTury);
                czl.setCooldownUmiejetnosci(umiejetnoscCooldown);
            }
        }
    }

    public int getNumerTury(){
        return numerTury;
    }
}
