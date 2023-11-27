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
     * Konstanty označující určitá celá čísla pro kontroly, aby se třída vyhnula magickým číslem v kódu
     */
    private static final int NULA = 0;
    private static final int JEDNICKA = 1;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    /**
     * Kritérium porovnávání prvků (tj. priorita)
     */
    private final Comparator<E> komparator;
    /**
     * Pole pro ukládání prvků haldy
     */
    private Object[] halda;
    /**
     * Maximální možná velikost haldy
     */
    private final int kapacita;
    /**
     * Aktuální velikost haldy (tj. počet prvků)
     */
    private int mohutnost;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Konstruktory">
    /**
     * Konstanta reprezentuje výchozí inicializační hodnotu kapacity haldy
     */
    private static final int VYCHOZI_INICIALIZACNI_KAPACITA = 11;

    public AbstrHeap(int kapacita,
                     Comparator<E> komp) throws HeapException {
        pozadatPlatnouKapacitu(kapacita);
        pozadatNePrazdnyKomparator(komp);

        this.komparator = komp;
        this.halda = new Object[kapacita];
        this.kapacita = kapacita;
        this.mohutnost = NULA;
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
        for (int i = pulka; i >= NULA; i--) {
            int aktualniIndex = i;
            while (aktualniIndex < delkaPole / 2) {
                final int indexLevehoSyna = indexLevehoSyna(aktualniIndex);
                final int indexPravehoSyna = indexPravehoSyna(aktualniIndex);

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
                pole[indexLevehoSyna].compareTo(pole[nejlepsiIndex]) > NULA;
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
                pole[indexPravehoSyna].compareTo(pole[nejlepsiIndex]) > NULA;
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
    public int mohutnost() { return mohutnost; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vloz(E prvek)">
    private static final int CINITEL_KAPACITY = 2;

    /**
     * Vloží prvek do haldy a zajistí udržení haldového uspořádaní
     *
     * <p> Popis jednotlivých bloků kódu:
     * <ol>
     * <li> <b>if(mohutnost == kapacita)</b>: Pokud je aktuální mohutnost větší než kapacita:
     *      <ul>
     *      <li> <b>zvysKapacitu()</b>: Zvětší kapacitu haldy
     *      </ul>
     * <li> Inkrementuje mohutnost a vloží prvek na konec pole haldy
     *      <ul>
     *      <li> <b>mohutnost++</b>
     *      <li> <b>halda[mohutnost - 1] = prvek</b>
     *      </ul>
     * <li> <b>probublejNahoru(mohutnost - 1)</b>: Probublá nově vložený prvek nahoru, aby byla zachována
     * haldové uspořádaní
     * </ol>
     */
    @Override
    public void vloz(E prvek) throws HeapException {
        pozadatNeprazdnyPrvek(prvek);

        zabezpecKapacitu();
        halda[mohutnost] = prvek;
        mohutnost++;
        probublejNahoru();
    }

    /**
     * Zdvojnásobí velikost pole, pokud se rovná celkocé kapacitě - <i>(eng. grow(int minCapacity))</i>
     *
     * <p> Kopírování prvků <b>System.arraycopy(halda, NULA, novaHalda, NULA, mohutnost)</b>:
     * <ol>
     * <li> <b>src</b>: Zdrojové pole, z něhož budou kopírovány prvky
     * <li> <b>srcPos</b>: Počáteční pozice v zdrojovém poli, odkud začne kopírování
     * <li> <b>dest</b>: Cílové pole, do něhož budou kopírovány prvky
     * <li> <b>destPos</b>: Počáteční pozice v cílovém poli, kam začne kopírování
     * <li> <b>length</b>: Počet prvků, jež budou zkopírovány
     * </ol>
     */
    private void zabezpecKapacitu() {
        if (mohutnost == kapacita) {
            int novaKapacita = halda.length * CINITEL_KAPACITY;
            Object[] novaHalda = new Object[novaKapacita];
            System.arraycopy(halda, NULA, novaHalda, NULA, mohutnost);
            halda = novaHalda;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: E odeberMax()">
    /**
     * Odebere a vrátí prvek s maximální hodnotou z haldy a zajistí udržení haldového uspořádaní
     *
     * <p> Popis jednotlivých bloků kódu:
     * <ol>
     * <li> <b>final E maxPrvek = (E) halda[0]</b>: Uloží prvek s nejvyšší prioritou (je vždy na začátku pole)
     * <li> Nahrazuje první prvek posledním a sníží mohutnost:
     *      <ul>
     *      <li> <b>halda[0] = halda[mohutnost - 1]</b>
     *      <li> <b>mohutnost--</b>
     *      </ul>
     * <li> <b>probublejDolu(0)</b>: Probublá dolu <i>(eng. heapifyDown)</i> na pozici, kde byl odebrán prvek
     * </ol>
     */
    @Override
    public E odeberMax() throws HeapException {
        pozadatNePrazdnouHaldu();

        final E maxPrvek = (E) halda[NULA];
        halda[NULA] = halda[--mohutnost];
        probublejDolu();

        return maxPrvek;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: E zpristupniMax()">
    @Override
    public E zpristupniMax() throws HeapException {
        pozadatNePrazdnouHaldu();
        return (E) halda[NULA];
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: String vypis()">
    @Override
    public String vypis(ETypProhl typ) {
        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody: Dotaz na null/prázdnost">
    /**
     * Zkontroluje, zda vstupní pole reprezentující haldu je prázdné
     *
     * @param pole Neuspořádaná halda
     *
     * @throws HeapException Když je pole prvků roveno {@code null}, anebo nemá žádné prvky
     *
     * @see AbstrHeap#vybuduj(Comparable[])
     */
    private void pozadatNeprazdnePole(E[] pole) throws HeapException {
        if (pole == null || pole.length == NULA)
            throw new HeapException(
                    HeapZprava.PRAZDNE_POLE.zprava());
    }

    /**
     * Zkontroluje, zda vstupní prvek je {@code null}
     *
     * @param prvek Prvek pro vložení
     *
     * @throws HeapException Když je vstupní prvek {@code null}
     *
     * @see AbstrHeap#vloz(Comparable)
     */
    private void pozadatNeprazdnyPrvek(E prvek) throws HeapException {
        if (prvek == null)
            throw new HeapException(
                    HeapZprava.PRAZDNY_PRVEK.zprava());
    }

    /**
     * Zkontroluje, zda vstupní inicializační kapacita větší než {@code 0}
     *
     * @param kapacita Inicializační kapacita vstupující do konstruktoru třídy
     *
     * @throws HeapException Když je vstupní kapacita menší než {@code 1}
     *
     * @see AbstrHeap#AbstrHeap(int, Comparator)
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
     *
     * @see AbstrHeap#AbstrHeap(int, Comparator)
     */
    private void pozadatNePrazdnyKomparator(Comparator<E> komp) throws HeapException {
        if (komp == null)
            throw new HeapException(
                    HeapZprava.NULL_INICIALIZACNI_KOMPARATOR.zprava());
    }

    /**
     * Zkontroluje, zda prioritní fronta je prázdná
     *
     * @throws HeapException Když je halda prázdná
     *
     * @see AbstrHeap#odeberMax()
     */
    private void pozadatNePrazdnouHaldu() throws HeapException {
        if (jePrazdna())
            throw new HeapException(
                    HeapZprava.PRAZDNA_HALDA.zprava());
    }
// </editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní Metody: Probublání prvků">
    /**
     * Probublá prvek (právě vložený na konec haldy) nahoru tak dlouho, dokud není splněno haldové uspořádání,
     * tzn. předek má větší nebo rovnou prioritu vůči svým potomkům <i>(eng. bubbleUp/heapifyUp/siftUp)</i>
     *
     * @see AbstrHeap#vloz(Comparable)
     */
    private void probublejNahoru() {
        int aktualniIndex = mohutnost - JEDNICKA;
        while (jePredek(aktualniIndex) &&
                komparator.compare(predek(aktualniIndex),
                                   (E) halda[aktualniIndex]) < NULA) {
            vymen(indexPredka(aktualniIndex), aktualniIndex);
            aktualniIndex = indexPredka(aktualniIndex);
        }
    }

    /**
     * Probublá prvek (právě odebraný - první prvek) dolu tak dlouho, dokud není splněno haldové uspořádání
     * <i>(bubbleDown/heapifyDown/siftDown)</i>
     *
     * @see AbstrHeap#odeberMax()
     */
    private void probublejDolu() {
        int indexAktualnihoUzlu = NULA;

        while (!isLeaf(indexAktualnihoUzlu)) {
            int leftChild = indexLevehoSyna(indexAktualnihoUzlu);
            int rightChild = indexPravehoSyna(indexAktualnihoUzlu);
            int nejlepsi = indexAktualnihoUzlu;

            if (leftChild < mohutnost() && komparator.compare((E) halda[leftChild], (E) halda[nejlepsi]) > 0) {
                nejlepsi = leftChild;
            }

            if (rightChild < mohutnost() && komparator.compare((E) halda[rightChild], (E) halda[nejlepsi]) > 0) {
                nejlepsi = rightChild;
            }

            if (nejlepsi != indexAktualnihoUzlu) {
                vymen(indexAktualnihoUzlu, nejlepsi);
                indexAktualnihoUzlu = nejlepsi;
            } else {
                break;
            }
        }

//        while (jeLevySyn(indexAktualnihoUzlu)) {
//            int indexNejmensihoSyna = indexLevehoSyna(indexAktualnihoUzlu);
//            final int vztahSynu = komparator.compare(pravySyn(indexAktualnihoUzlu),
//                                                     levySyn(indexAktualnihoUzlu));
//            if (jePravySyn(indexAktualnihoUzlu) && vztahSynu < NULA)
//                indexNejmensihoSyna = indexPravehoSyna(indexAktualnihoUzlu);
//
//            final int vztahPredka = komparator.compare((E) halda[indexAktualnihoUzlu],
//                                                       (E) halda[indexNejmensihoSyna]);
//            if (vztahPredka < NULA) {
//                vymen(indexAktualnihoUzlu, indexNejmensihoSyna);
//            } else {
//                break;
//            }
//            indexAktualnihoUzlu = indexNejmensihoSyna;
//        }
    }

    private boolean isLeaf(int pos)
    {
        return pos > (mohutnost / 2) && pos <= mohutnost;
    }

    /**
     * Nalezne pozice největšího syna dané pozice v kontextu binární haldy
     *
     * <p> Popis jednotlivých bloků kódu:
     * <ol>
     * <li> <b>if(indexLevehoSyna < mohutnost() && vztahSLevymSynem > 0)</b>:
     *      <ul>
     *      <li> Porovnává prioritu levého syna s prioritou aktuální pozice
     *      </ul>
     * <li> <b>if(indexPravehoSyna < mohutnost() && vztahSPravymSynem > 0)</b>:
     *      <ul>
     *      <li> Porovnává prioritu pravého syna s prioritou:
     *          <ol>
     *          <li> buď: aktuální pozice
     *          <li> nebo: levého syna (pokud již prohodila)
     *          </ol>
     *      </ul>
     * </ol>
     *
     * @param pozPredka Index aktuálního uzlu v poli, pro nějž hledá největšího (z hlediska priority) syna
     *
     * @return Index syna s nejvyšší prioritou (tj. v souladu s porovnávacím komparátorem)
     *
     * @see AbstrHeap#probublejDolu()
     */
    private int najdiNejlepsihoSyna(int pozPredka) {
        int indexLevehoSyna = indexLevehoSyna(pozPredka);
        int indexPravehoSyna = indexPravehoSyna(pozPredka);
        int indexNejlepsihoSyna = pozPredka;

        final int vztahSLevymSynem = komparator.compare((E) halda[indexLevehoSyna],
                                                        (E) halda[indexNejlepsihoSyna]);
        if (indexLevehoSyna < mohutnost() && vztahSLevymSynem > NULA)
            indexNejlepsihoSyna = indexLevehoSyna;

        final int vztahSPravymSynem = komparator.compare((E) halda[indexPravehoSyna],
                                                         (E) halda[indexNejlepsihoSyna]);
        if (indexPravehoSyna < mohutnost() && vztahSPravymSynem > NULA)
            indexNejlepsihoSyna = indexPravehoSyna;

        return indexNejlepsihoSyna;
    }

    /**
     * Provádí výměnu prvků na zadaných pozicích
     *
     * @param poz1 Pozice <i>(position)</i> prvního prvku
     * @param poz2 Pozice <i>(position)</i> druhého prvku
     *
     * @see AbstrHeap#probublejNahoru()
     * @see AbstrHeap#probublejDolu()
     */
    private void vymen(int poz1, int poz2) {
        final Object docasna = halda[poz1];
        halda[poz1] = halda[poz2];
        halda[poz2] = docasna;
    }
// </editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní Metody: Přístup k prvkům">
    /**
     * Výpočet pozic levého syna, pravého syna a předka závisí na tom, jak jsou indexy pole <i>(případně uzlů v
     * binárním stromu)</i> číslovány. Jsou používány dvě konvence:
     * <ol>
     * <li> <b>Nula-indexované pole</b>:
     *      <ul>
     *      <li> Levý syn: {@code 2 * pos + 1}
     *      <li> Pravý syn: {@code 2 * pos + 2}
     *      <li> Předek: {@code (pos - 1) / 2}
     *      </ul>
     * <li> <b>Jedna-indexované pole</b>:
     *      <ul>
     *      <li> Levý syn: {@code 2 * pos}
     *      <li> Pravý syn: {@code 2 * pos + 1}
     *      <li> Předek: {@code pos / 2}
     *      </ul>
     * </ol>
     * <b>Poznámka</b>: V praxi je běžné používat <b>nula-indexované pole</b>, což odpovídá běžné praxi v
     * programování v Javě, jež indexuje pole <i>(array)</i> od nuly
     */
    private int indexLevehoSyna(int indexPredka) {
        return (2 * indexPredka) + 1;
    }

    private int indexPravehoSyna(int indexPredka) {
        return (2 * indexPredka) + 2;
    }

    private int indexPredka(int indexSyna) {
        return indexSyna / 2;
    }

    /**
     * Gettery pro předka a jeho syny
     */
    private E levySyn(int indexPredka) {
        return (E) halda[indexLevehoSyna(indexPredka)];
    }

    private E pravySyn(int indexPredka) {
        return (E) halda[indexPravehoSyna(indexPredka)];
    }

    private E predek(int indexSyna) {
        return (E) halda[indexPredka(indexSyna)];
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody: Zjišťování existence předka/synů">
    private boolean jeLevySyn(int indexPredka) {
        return indexLevehoSyna(indexPredka) < mohutnost();
    }

    private boolean jePravySyn(int indexPredka) {
        return indexPravehoSyna(indexPredka) < mohutnost();
    }

    private boolean jePredek(int indexSyna) {
        return indexPredka(indexSyna) >= 0;
    }
// </editor-fold>
}
