package cz.upce.fei.bdats.halda;

import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.zpravy.HeapZprava;
import org.jetbrains.annotations.NotNull;
import cz.upce.fei.bdats.vyjimky.HeapException;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Třída implementuje sadu základních operací abstraktní datové strultury <i>(ADS)</i> <b>prioritní fronta</b>
 * rozšiřující ADS <b>binární vyhledávací strom</b> <i>(BVS - eng. Binary Search Tree)</i>
 *
 * <p> Třída implementuje <b>binární maximální levostrannou haldu</b> <i>(eng. Max Heap)</i>, což je binárním stromem,
 * kde hodnota každého předka je větší nebo rovna hodnotám jeho potomků <i>(levý a pravý syny)</i>, což zajišťuje
 * rychlý přístup k největšímu prvku v množině
 *
 * <p> Implementuje rozhraní {@link IAbstrHeap}
 *
 * @see PriorityQueue
 *
 * @param <E> Generický parametr reprezentující budoucí datový typ prvků prioritní fronty, které musí být
 *        porovnatelné podle určité priority
 */
public final class AbstrHeap<E extends Comparable<E>> implements IAbstrHeap<E> {

// <editor-fold defaultstate="collapsed" desc="Konstanty">
    /**
     * Konstanty používany při výpočtu pozicí levého a pravého synů vůči jejich předku
     * (tedy vrcholu s indexem {@code i}):
     * <ul>
     * <li> Levý potomek má index {@code 2i +1};
     * <li> Pravý potomek má index {@code 2i + 2};
     * <li> Předek má index {@code i / 2}
     *      <ul>
     *      <li> V případě znalosti indexu syna (i): {@code (i - 1) / 2}
     *      </ul>
     * </ul>
     */
    private static final int CINITEL_POZICE = 2;
    private static final int SCITANEC_LEVEHO_SYNA = 1;
    private static final int SCITANEC_PRAVEHO_SYNA = 2;

    private static final int INDEX_KOREN = 0;
    private static final int MENSITEL_PREDKA = 1;
    private static final int DELITEL_PREDKA = 2;

    /**
     * Konstanta reprezentuje výchozí inicializační hodnotu kapacity haldy
     */
    private static final int VYCHOZI_INICIALIZACNI_KAPACITA = 11;

    /**
     * Konstanty označující určitá celá čísla pro kontroly, aby se třída vyhnula magickým číslem v kódu
     */
    private static final int NULTA_HODNOTA = 0;
    private static final int JEDNICKA = 1;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    /**
     * Kritérium porovnávání prvků (tj. priorita)
     */
    private final Comparator<E> komparator;
    /**
     * Maximální možná velikost haldy
     */
    private final int kapacita;
    /**
     * Aktuální velikost haldy (tj. počet prvků)
     */
    private int mohutnost;
    /**
     * Pole pro ukládání prvků haldy
     */
    private Object[] halda;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Konstruktory">
    public AbstrHeap(int kapacita,
                     Comparator<E> komp) throws HeapException {
        pozadatPlatnouKapacitu(kapacita);
        pozadatNePrazdnyKomparator(komp);

        this.kapacita = kapacita;
        this.halda = new Object[kapacita];
        this.komparator = komp;
    }

    public AbstrHeap(Comparator<E> komp) throws HeapException {
        this(VYCHOZI_INICIALIZACNI_KAPACITA, komp);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vybuduj(E[] pole)">
    @Override
    public void vybuduj(E[] pole) throws HeapException {
        pozadatNeprazdnePole(pole);

        final int delkaPole = pole.length;
        final int pulka = delkaPole / 2 - 1;
        for (int i = pulka; i >= NULTA_HODNOTA; i--) {
            int aktualniIndex = i;
            while (aktualniIndex < delkaPole / 2) {
                final int indexLevehoSyna = dejIndexLevehoSyna(aktualniIndex);
                final int indexPravehoSyna = dejIndexPravehoSyna(aktualniIndex);

                int nejlepsiIndex = aktualniIndex;
                if (jeLevySynLepsi(indexLevehoSyna, pole, nejlepsiIndex))
                    nejlepsiIndex = indexLevehoSyna;
                if (jePravySynLepsi(indexPravehoSyna, pole, nejlepsiIndex))
                    nejlepsiIndex = indexPravehoSyna;

                if (nejsouStejneIndexy(nejlepsiIndex, aktualniIndex)) {
                    final E docasna = pole[aktualniIndex];
                    pole[aktualniIndex] = pole[nejlepsiIndex];
                    pole[nejlepsiIndex] = docasna;
                    aktualniIndex = nejlepsiIndex;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Zkontroluje, zda levý potomek existuje a má větší prioritu
     *
     * @param indexLevehoSyna Index levého potomka
     * @param pole Pole prvků reprezentujících haldu
     * @param nejlepsiIndex Index prvku s nejlepší prioritou
     *
     * @return {@code true}, pokud levý potomek existuje a má větší prioritu, jinak {@code false}
     */
    private boolean jeLevySynLepsi(int indexLevehoSyna,
                                   E @NotNull [] pole,
                                   int nejlepsiIndex) {
        return indexLevehoSyna < pole.length &&
                pole[indexLevehoSyna].compareTo(pole[nejlepsiIndex]) > NULTA_HODNOTA;
    }

    /**
     * Zkontroluje, zda pravý potomek existuje a má větší prioritu
     *
     * @param indexPravehoSyna Index pravého potomka
     * @param pole Pole prvků reprezentujících haldu
     * @param nejlepsiIndex Index prvku s nejlepší prioritou
     *
     * @return {@code true}, pokud pravý potomek existuje a má větší prioritu, jinak {@code false}
     */
    private boolean jePravySynLepsi(int indexPravehoSyna,
                                    E @NotNull [] pole,
                                    int nejlepsiIndex) {
        return indexPravehoSyna < pole.length &&
                pole[indexPravehoSyna].compareTo(pole[nejlepsiIndex]) > NULTA_HODNOTA;
    }

    /**
     * Zkontroluje, zda indexy nejlepšího a aktuálního prvku nejsou stejné
     *
     * @param nejlepsiIndex Index prvku s nejlepší prioritou
     * @param aktualniIndex Aktuální index prvku
     *
     * @return {@code true}, pokud indexy nejsou stejné, jinak {@code false}
     */
    private boolean nejsouStejneIndexy(int nejlepsiIndex,
                                       int aktualniIndex) {
        return nejlepsiIndex != aktualniIndex;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void reorganizuj(Comparator<E> comp)">
    @Override
    public void reorganizuj(Comparator<E> komp) throws HeapException {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void zrus()">
    @Override
    public void zrus() {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: boolean jePrazdna()">
    @Override
    public boolean jePrazdna() {
        return false;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: int mohutnost()">
    @Override
    public int mohutnost() {
        return 0;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vloz(E prvek)">
    @Override
    public void vloz(E prvek) throws HeapException {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: E odeberMax()">
    @Override
    public E odeberMax() throws HeapException {
        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: E zpristupniMax()">
    @Override
    public E zpristupniMax() throws HeapException {
        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: String vypis()">
    @Override
    public String vypis(ETypProhl typ) {
        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody: Žádosti na null/prázdnost">
    /**
     * Zkontroluje, zda vstupní pole reprezentující haldu je prázdné
     *
     * @param pole Neuspořádaná halda
     *
     * @throws HeapException Když je pole prvků roveno {@code null}, anebo nemá žádné prvky
     */
    private void pozadatNeprazdnePole(E[] pole) throws HeapException {
        if (pole == null || pole.length == NULTA_HODNOTA)
            throw new HeapException(
                    HeapZprava.PRAZDNE_POLE.zprava());
    }

    /**
     * Zkontroluje, zda vstupní inicializační kapacita větší než {@code 0}
     *
     * @param kapacita Inicializační kapacita vstupující do konstruktoru třídy
     *
     * @throws HeapException Když je vstupní kapacita menší než {@code 1}
     */
    private void pozadatPlatnouKapacitu(int kapacita) throws HeapException {
        if (kapacita < JEDNICKA)
            throw new HeapException(
                    HeapZprava.NULL_INICIALIZACNI_KAPACITA.zprava());
    }

    /**
     * Zkontroluje, zda vstupní komparátor je {@code null}
     *
     * @param komp Inicializační porovnájací kritérium (priorita), podle něhož se následně vybuduje halda
     *
     * @throws HeapException Když je vstupní komparátor {@code null}
     */
    private void pozadatNePrazdnyKomparator(Comparator<E> komp) throws HeapException {
        if (komp == null)
            throw new HeapException(
                    HeapZprava.NULL_INICIALIZACNI_KOMPARATOR.zprava());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní zjišťovací metody">
    private int dejIndexLevehoSyna(int indexPredka) {
        return (CINITEL_POZICE * indexPredka) + SCITANEC_LEVEHO_SYNA;
    }

    private int dejIndexPravehoSyna(int indexPredka) {
        return (CINITEL_POZICE * indexPredka) + SCITANEC_PRAVEHO_SYNA;
    }

    private int indexPredka(int indexSyna) {
        return (indexSyna - MENSITEL_PREDKA) / DELITEL_PREDKA;
    }

    private boolean jeLevySyn(int indexPredka) {
        return dejIndexLevehoSyna(indexPredka) < mohutnost();
    }

    private boolean jePravySyn(int indexPredka) {
        return dejIndexPravehoSyna(indexPredka) < mohutnost();
    }

    private boolean jePredek(int indexSyna) {
        return indexPredka(indexSyna) >= INDEX_KOREN;
    }
// </editor-fold>
}
