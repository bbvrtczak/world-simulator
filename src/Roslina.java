import java.util.Objects;

public abstract class Roslina extends Organizm{

    public Roslina(Organizm other){
        sila = other.getSila();
        wiek = 0;
        swiat = other.getSwiat();
    }

    public Roslina(){
        sila = 0;
        wiek = 0;
        swiat = null;
    }

    public abstract String organizmToString();

    public void kolizja(Organizm org){
        Organizm jedzonaRoslina = this.getSwiat().getOrganizmNaPozycji(this.getPozycja());

        this.getSwiat().dodajKomentarz(org.toString() + " zjadl " + this.organizmToString());

        jedzonaRoslina.specyfikaKolizji(org);

        jedzonaRoslina.zabij();
    }

    public void rozsianie() {
        if (swiat.organizmy.size() < swiat.getSzerokosc() * swiat.getWysokosc()) {
            Punkt p = new Punkt(-1, -1);

            Organizm nowaRoslina;

            Punkt nowePole = new Punkt(-1, -1);
            nowePole = this.getSwiat().losujWolneSasiedniePole(this.getPozycja());

            if (Objects.equals(nowePole, p))
                return;

            for (Organizm org : this.swiat.getDodaneOrganizmy()) {
                if (nowePole == org.getPozycja())
                    return;
            }

            nowaRoslina = this.swiat.stworzOrganizm(this.organizmToString(), nowePole, swiat);
            this.swiat.dodajOrganizmTymczasowy(nowaRoslina);
            this.swiat.dodajKomentarz(this.organizmToString() + " rozrosl sie");
        }
    }
}
