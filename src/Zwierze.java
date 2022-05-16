import java.util.Objects;

public abstract class Zwierze extends Organizm {

    private static final int NIE_PORUSZA_SIE = 0;

    public abstract String organizmToString();

    protected Zwierze(){
        sila = 0;
        wiek = 0;
        swiat = null;
    }

    protected Zwierze(Organizm other){
        sila = other.getSila();
        wiek = 0;
        swiat = other.getSwiat();
    }

    public void atak(Organizm atakowany){
        Organizm atakujacy = swiat.getOrganizmNaPozycji(getPozycja());
        atakowany.specyfikaKolizji(atakujacy);

        if (getSila() > atakowany.getSila()) {
            if (!atakowany.czyNiesmiertelny()) {
                swiat.dodajKomentarz(atakujacy.organizmToString() + " zabil " + atakowany.organizmToString());
                atakowany.zabij();
            }
        }
        else if (getSila() == atakowany.getSila()){
            if (getWiek() >= atakowany.getWiek()){
                if (!atakowany.czyNiesmiertelny()){
                    swiat.dodajKomentarz(atakujacy.organizmToString() + " zabil " + atakowany.organizmToString());
                    atakowany.zabij();
                }
            }
        }
        else{
            if (!atakujacy.czyNiesmiertelny()){
                swiat.dodajKomentarz(atakowany.organizmToString() + " zabil " + atakujacy.organizmToString());
                atakujacy.zabij();
            }
        }

        atakowany.zmniejszNiesmiertelnosc();
        atakujacy.zmniejszNiesmiertelnosc();
    }

    @Override
    public void kolizja(Organizm organizmNaNowejPozycji){
        Organizm organizmAtakujacy = swiat.getOrganizmNaPozycji(getPozycja());
        if (organizmNaNowejPozycji instanceof Czlowiek && this instanceof Czlowiek){
            return;
        }
        if (organizmNaNowejPozycji instanceof Roslina) {
            organizmNaNowejPozycji.kolizja(organizmAtakujacy);
        }
        else{
            if (Objects.equals(organizmAtakujacy.organizmToString(), organizmNaNowejPozycji.organizmToString()) && !(organizmAtakujacy instanceof Czlowiek)){
                if (organizmAtakujacy.getIleDoRozmnozenia() == 0 && organizmNaNowejPozycji.getIleDoRozmnozenia() == 0){
                    if (!swiat.czyJestWolnePole()){
                        czySieRozmnozyl = true;
                        return;
                    }
                    rozmnozenie();
                    organizmAtakujacy.rozmnozylSie();
                    organizmNaNowejPozycji.rozmnozylSie();
                }
                else {
                    return;
                }
            }
            else
                atak(organizmNaNowejPozycji);
        }
    }

    void ruch(Punkt nowePole){
        if (this instanceof Czlowiek){
            if (nowePole == getPozycja()) {
                return;
            }
        }

        Organizm organizmNaNowejPozycji = swiat.getOrganizmNaPozycji(nowePole);

        if (organizmNaNowejPozycji != null) {
            kolizja(organizmNaNowejPozycji);
        }

        if (czySieRozmnozyl) {
            czySieRozmnozyl = false;
            return;
        }

        if (czySieRuszy()){
            setPozycja(nowePole);
        }
    }

    void rozmnozenie(){
        Punkt poleNowegoOrganizmu = swiat.losujWolneSasiedniePole(getPozycja());
        Punkt p = new Punkt(-1,-1);

        if (poleNowegoOrganizmu == p){
            return;
        }

        for (Organizm org : swiat.getDodaneOrganizmy()){
            if (poleNowegoOrganizmu == org.getPozycja()){
                return;
            }
        }

        Organizm nowyOrganizm = swiat.stworzOrganizm(organizmToString(), poleNowegoOrganizmu, getSwiat());
        nowyOrganizm.rozmnozylSie();

        swiat.dodajOrganizmTymczasowy(nowyOrganizm);
        czySieRozmnozyl = true;
        swiat.dodajKomentarz(organizmToString() + " rozmnozyl sie");
    }

    public abstract String show();

}
