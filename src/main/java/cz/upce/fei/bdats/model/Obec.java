package cz.upce.fei.bdats.model;

//<editor-fold defaultstate="collapsed" desc="Importy">
import org.jetbrains.annotations.NotNull;
import cz.upce.fei.bdats.validatory.IntegerValidator;
import cz.upce.fei.bdats.validatory.TextValidator;
import cz.upce.fei.bdats.validatory.Validator;
import cz.upce.fei.bdats.vyjimky.ObecException;
import cz.upce.fei.bdats.vyjimky.zpravy.ObecZprava;

import java.io.ObjectStreamClass;
import java.io.Serial;
import java.io.Serializable;
// </editor-fold>

/**
 * Třída reprezentuje informací o obci
 *
 * <p> Třída používá validační funkcionální interfejsy prostřednictvím kterých jsou ověřovány vstupní hodnoty
 * parametrů konstruktoru, což umožňuje flexibilní nastavení validačních pravidel při konstrukci instance třídy
 *
 * <p> <b>Poznámka</b>: Alternativně by se tady dalo využit záznamovou třídu {@code record} sloužící k reprezentaci a uchování
 * dat a jsou třídy tohoto typu navrženy tak, aby byly jednoduché, neměnné a automaticky generovaly určité
 * metody, jako jsou:
 * <ul>
 * <li> {@code konstruktor};
 * <li> {@code gettery} pro získání hodnoty každého atributu;
 * <li> {@link Object#toString()} vracející textovou reprezentaci instance obsahující názvy atributů a jejich
 * hodnoty;
 * <li> {@link Object#equals(Object)} porovnávající dvě instance record třídy na základě všech jejich atributů
 * a vrací {@code true}, pokud jsou všechny hodnoty shodné;
 * <li> {@link Object#hashCode()} generující hash kód pro instanci na základě hodnot atributů, přičemž dvě
 * shodné instance budou mít stejný hash kód
 * </ul>
 * V rámci record třídy jsou atributy implicitně označeny modifikátorem {@code final}, což znamená, že nemohou
 * být po vytvoření instance změněny
 *
 * <p> Tato implementace není ale případ záznamové třídy kvůli možnosti vystavení konstruktorem osobní výjimky
 * {@link ObecException} v případě špatně zadaných hodnot pro argumenty tohoto konstruktoru
 */
public final class Obec implements Serializable {

    /**
     * Konstanta reprezentuje identifikační číslo verze serializace pro tuto třídu
     *
     * <p> Serial Version UID jednoznačně identifikuje verze třídy, čímž zajišťuje kompatibilitu mezi různými
     * verzemi tříd. Tzn. provádí kontrolu, zda se starší verze třídy shoduje s novější verzí při deserializaci.
     * Výjimka {@link java.io.InvalidClassException} indikuje nekompatibilitu
     */
    @Serial
    private static final long serialVersionUID = ObjectStreamClass.lookup(Obec.class).getSerialVersionUID();

    /**
     * Konstanty reprezentující validátory pro řetězce/celá čísla jsou používány k ověření platnosti řetězcových/
     * celočíselných atributů třídy
     */
    private static final Validator<String> validatorStringu = new TextValidator();
    private static final Validator<Integer> validatorIntegeru = new IntegerValidator();

//<editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    private final int cisloKraje;
    private final String nazevKraje;
    private final String nazevObce;
    private final String psc;
    private final int pocetMuzu;
    private final int pocetZen;
    private final int celkem;
// </editor-fold>

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public Obec(int cisloKraje,
                String nazevKraje,
                String nazevObce,
                String psc,
                int pocetMuzu,
                int pocetZen,
                int celkem) throws ObecException {
        if (!validatorStringu.validuj(nazevKraje, nazevObce, psc))
            throw new ObecException(ObecZprava.PRAZDNY_RETEZEC.zprava());

        if (!validatorIntegeru.validuj(cisloKraje, pocetMuzu, pocetZen, celkem))
            throw new ObecException(ObecZprava.ZAPORNA_HODNOTA.zprava());

        if (!validujAtributCelkem(pocetMuzu, pocetZen, celkem))
            throw new ObecException(ObecZprava.NEPLATNY_CELY_POCET.zprava());

        this.cisloKraje = cisloKraje;
        this.nazevKraje = nazevKraje;
        this.nazevObce = nazevObce;
        this.psc = psc;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = celkem;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="toString()">
    /**
     * Sestaví informace o stavu
     *
     * @return Obec{cisloKraje=10, nazevKraje='Pardubický', nazevObce='a', psc='8113', pocetMuzu=1, pocetZen=1, celkem=2}
     */
    @Override
    public @NotNull String toString() {
        return "Obec{" +
                "cisloKraje=" + cisloKraje +
                ", nazevKraje='" + nazevKraje + '\'' +
                ", nazevObce='" + nazevObce + '\'' +
                ", psc='" + psc + '\'' +
                ", pocetMuzu=" + pocetMuzu +
                ", pocetZen=" + pocetZen +
                ", celkem=" + celkem +
                '}';
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní metody">
    /**
     * Privátní pomocní metoda pro skrývání implementaci konstruktoru třídy
     *
     * @return {@code true}, pokud hodnota atributu {@code celkem} vyjadřuje součet hodnot atributů {@code pocetMuzu}
     * + {@code pocetZen}
     */
    private boolean validujAtributCelkem(int pocetMuzu,
                                         int pocetZen,
                                         int celkem) {
        return pocetMuzu + pocetZen == celkem;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Gettery/Přístupové metody ke stavu">
    public int getCisloKraje() { return cisloKraje; }

    public String getNazevKraje() { return nazevKraje; }

    public String getNazevObce() { return nazevObce; }

    public String getPsc() { return psc;}

    public int getPocetMuzu() { return pocetMuzu; }

    public int getPocetZen() { return pocetZen; }

    public int getCelkem() { return celkem; }
//</editor-fold>
}
