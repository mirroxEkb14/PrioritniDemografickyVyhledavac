package cz.upce.fei.bdats.sekvence.seznam;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.vyjimky.DoubleListException;
import cz.upce.fei.bdats.vyjimky.zpravy.DoubleListZprava;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
// </editor-fold>

/**
 * Třída implementuje sadu základích operací abstraktní datové struktury <i>(ADS)</i> <b>obousměrně
 * necyklicky zřetězený lineární seznam</b> s organizací s explicitními vztahy, tzn. prvky mají explicitní
 * referenci a vztahy se neřeší aritmetickým výpočtem (implicitní)
 *
 * <p> Třída reprezentuje dynamickou datovou strukturu skládající se z lineárně uspořádaných prvků (nodes)
 * vzájemně propojených ukazateli
 *
 * <p> Implementuje rozhraní {@link IAbstrDoubleList}
 *
 * @param <T> Generický parametr reprezentující budoucí datový typ
 */
public final class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    /**
     * Ukazatel na první prvek je stupním bodem do spojového seznamu (head)
     */
    private Prvek<T> hlavicka;
    /**
     * Ukazatel na konec seznamu je vhodný pro snadné přidávání prvků na konec seznamu (tail)
     */
    private Prvek<T> paticka;
    /**
     * Vnitřní aktuální ukazatel na data seznamu
     */
    private Prvek<T> aktualni;
    /**
     * Aktuální počet dat v seznamu
     */
    private int mohutnost;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Konstanty">
    /**
     * Konstanty používané při kontrole rozsahu seznamu:
     * <ul>
     * <li> Zda jsou vůbec nějaké prvky
     * <li> Zda je alespoň jeden prvek
     * </ul>
     *
     */
    private static final int NULTA_HODNOTA = 0;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Vnořená třída (nested class)">
    /**
     * Třída je pomocním zanořeným typem
     *
     * <p> Třída obsahuje dva propojovací atributy pro uložení odkazu směrem na další a předchozí instanci
     * téže třídy.
     *
     * <p> Každý prvek v sobě nese:
     * <ul>
     * <li> Datovou hodnotu - {@link Integer}, {@link String}, {@link Object} apod.
     * <li> Ukazatel na následující a předchozí prvky (metadata)
     * </ul>
     * Prvek spojového seznamu "obaluje" datovou hodnotu informacemi o jejím relativním umístění vůči ostatním
     * prvkům. V rámci každého nódu/prvku jsou uchovávaná samotná data a současně v tom nódu jsou metadata
     * ukazující na následující a předchozí nód
     *
     * @param <T> Datová hodnota
     */
    private static class Prvek<T> {
        Prvek<T> predchozi;
        Prvek<T> dalsi;
        T polozka;

        private Prvek(T polozka) {
            this.polozka = polozka;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void zrus()">
    /**
     * Inicializuje proměnnou {@code p} představující aktuálně zpracovávaný prvek, pak v cylku {@code for}
     * postupně prochází všechny prvky seznamu, během něhož uvolňuje paměť tohoto aktuálního prvku a následně
     * se přepíná na prvek další pro pokračování v tom cyklu, po čemž zrušuje zbývající odkazy na prvky seznamu
     * a nastavuje počet prvků na nulu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Začne cyklus {@code for} z hlavičky sezmanu:
     *     <ol>
     *     <li> Vytvoří prvek, který reprezentuje prvek další
     *     než prvek, který je na dané iteraci (při 1. iteraci je to hlavička)
     *     <li> Vynuluje položku prvku, který je na dané interaci
     *     <li> Vynuluje prvek další prvku na dané iteraci
     *     <li> Vynuluje prvek předchozí prvku na dané iteraci
     *     <li> Přestaví prvek na dané iteraci na prvek mu další, aby cyklus
     *     {@code for} se pokračoval
     *     </ol>
     * <li> Nastaví hlavičku na patičku, kterou predtím nastaví na aktuální,
     * který předtím vynuluje
     * <li> Vynuluje počet prvků v seznamu
     * </ol>
     *
     * @see AbstrDoubleList#vynulujMohutnost()
     */
    @Override
    public void zrus() {
        for (Prvek<T> p = hlavicka; p != null;) {
            Prvek<T> dalsi = p.dalsi;
            p.polozka = null;
            p.dalsi = null;
            p.predchozi = null;
            p = dalsi;
        }
        hlavicka = paticka = aktualni = null;
        vynulujMohutnost();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: boolean jePrazdny()">
    @Override
    public boolean jePrazdny() { return mohutnost == NULTA_HODNOTA; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: int mohutnost()">
    @Override
    public int mohutnost() { return mohutnost; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: Iterator<T> iterator()">
    /**
     * Prochází postupně všechny prvky seznamu posunutím aktuálního na další
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Vratí nový objekt {@link Iterator}:
     *      <ol>
     *      <li> Vytvoří nový prvek obsahující odkaz na první objekt seznamu
     *      <li> <b>hasNext()</b>:
     *          <ol>
     *          <li> Vrátí {@code true}, pokud aktuální prvek není {@code null}, v opačném
     *          případě {@code false}
     *          </ol>
     *      <li> <b>next()</b>:
     *          <ol>
     *          <li> Pokud metoda {@code hasNext()} vratí {@code true}, tak:
     *              <ol>
     *              <li> Vytvoří novou položku obsahující data z nyní aktuálního prvku
     *              <li> Nastaví aktuální prvek na prvek mu další
     *              <li> Vrátí data, která byla v aktuálním prvku před bodem (2)
     *              </ol>
     *          <li> Vyhodí výjimku, protože aktuální prvek obsahuje hodnotu {@code null}
     *          </ol>
     *      </ol>
     * </ol>
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return new Iterator<>() {

            Prvek<T> aktualniPrvek = hlavicka;

            @Override
            public boolean hasNext() {
                return aktualniPrvek != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = aktualniPrvek.polozka;
                    aktualniPrvek = aktualniPrvek.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }
        };
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vlozPrvni(T data)">
    /**
     * Pokud není seznam prázdný, vnoří se do podmíněný bloku {@code else}, kde provede vložení nového prvku,
     * přičemž ten nový prvek bude představovat aktualizovanou hlavičku a vytvoří obosměrný odkaz nastavením
     * ukazatelů předchozího a dalšího prvků
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Vytvoří nový uzel (node), který je potřeba vložit do seznamu
     * <li> Pokud je seznam prázdný, tak:
     *      <ol>
     *      <li> Nastaví hlavičku a patičku seznamu na novy prvek
     *      </ol>
     * <li> Pokud není seznam prázdný (hlavička není {@code null}), tak:
     *      <ol>
     *      <li> Nastaví další ukazatel nového uzlu na hlavičku
     *      <li> Nastaví předchozí ukazatel hlavičky na nový uzel
     *      <li> Nastaví hlavičku na nový prvek
     *      <li> <b>Poznámka:</b> Předchozí ukazatel nového uzlu je
     *      {@code null}, protože to bude nová hlavička
     *      </ol>
     * <li> Zvyší atribut {@code pocet} o jednu
     * </ol>
     *
     * @see AbstrDoubleList#zvysMohutnost()
     */
    @Override
    public void vlozPrvni(T data) throws DoubleListException {
        pozadatNeNull(data);

        final Prvek<T> novyPrvek = new Prvek<>(data);
        if (hlavicka == null) {
            hlavicka = paticka = novyPrvek;
        } else {
            novyPrvek.dalsi = hlavicka;
            hlavicka.predchozi = novyPrvek;
            hlavicka = novyPrvek;
        }
        zvysMohutnost();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vlozPosledni(T data)">
    /**
     * Pokud není seznam prázdný, vnoří se do podmíněný bloku {@code else}, kde provede vložení prvku na
     * poslední pozici, čímž aktualizuje patičku seznamu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Vytvoří nový node
     * <li> Pokud je seznam prázdný, tak:
     *      <ol>
     *      <li> Nastaví oboje hlavičku a patičku na nový node
     *      </ol>
     * <li> Pokud není seznam prázdný (patička není {@code null}), tak:
     *      <ol>
     *      <li> Nastaví další ukazatel posledního uzlu na nový node
     *      <li> Nastaví předchozí ukazatel nového uzlu na patičku, což
     *      je posledním uzelem
     *      <li> Nastaví patičku na nový node
     *      <li> <b>Poznámka:</b> Další ukazatel nového uzlu je {@code null},
     *      protože je posledním uzelem v seznamu
     *      </ol>
     * <li> Zvyší atribut {@code pocet} o jednu
     * </ol>
     *
     * @see AbstrDoubleList#zvysMohutnost()
     */
    @Override
    public void vlozPosledni(T data) throws DoubleListException {
        pozadatNeNull(data);

        final Prvek<T> novyPrvek = new Prvek<>(data);
        if (paticka == null) {
            hlavicka = paticka = novyPrvek;
        } else {
            paticka.dalsi = novyPrvek;
            novyPrvek.predchozi = paticka;
            paticka = novyPrvek;
        }
        zvysMohutnost();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vlozNaslednika(T data)">
    /**
     * Provede nastavení odkazů u nově vytvořeného prvku a následně aktualizuje odkaz na další prvek u
     * aktuálního. V případě, že byl aktuální prvek posledním prvkem v seznamu, nastaví paticku seznamu
     * na ten nový prvek
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ověří, zda je nastaven aktuální prvek
     * <li> Vytvoří nový prvek s novými daty
     * <li> Nastaví u nového prvku ukazatel na předchozí na aktuální
     * <li> Nastaví u nového prvku ukazatel na další na další prvek
     * nyní aktuálního
     * <li> Pokud prvek další aktuálního není {@code null}, tak:
     *     <ol>
     *     <li> Nastaví ukazatel předchozí u prvku další aktuálního
     *     na nový prvek
     *     <li> Nastaví prvek další aktuálnímu na nový prvek
     *     </ol>
     * <li> Pokud prvek další aktuálního je {@code null}, tak:
     *     <ol>
     *     <li> Nastaví patičku na prvek další aktuálnímu, který
     *     který předtím bude nastaven na nový prvek
     *     </ol>
     * <li> Zvyší počet prvků o jedničku
     * </ol>
     *
     * @see AbstrDoubleList#zvysMohutnost()
     */
    @Override
    public void vlozNaslednika(T data) throws DoubleListException {
        pozadatNeNull(data);
        pozadatAktualni();

        final Prvek<T> novyPrvek = new Prvek<>(data);
        novyPrvek.predchozi = aktualni;
        novyPrvek.dalsi = aktualni.dalsi;
        if (aktualni.dalsi != null) {
            aktualni.dalsi.predchozi = novyPrvek;
            aktualni.dalsi = novyPrvek;
        } else {
            paticka = aktualni.dalsi = novyPrvek;
        }
        zvysMohutnost();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vlozPredchudce(T data)">
    /**
     * Provede nastavení odkazů u nově vytvořeného prvku a následně aktualizuje odkaz na předchozí prvek
     * u aktuálního. V případě, že byl aktuální prvek prvním prvkem v seznamu, nastaví hlavičku seznamu
     * na ten nový prvek
     *
     * Popis logiky:
     * <ol>
     * <li> ověří, zda je nastaven aktuální prvek
     * <li> vytvoří nový prvek s novými daty
     * <li> nastaví u nového prvku ukazatel na další na aktuální
     * <li> nastaví u nového prvku ukazatel na předchozí na předchozí prvek
     * nyní aktuálního
     * <li> pokud prvek předchozí aktuálního není {@code null}, tak:
     *     <ol>
     *     <li> nastaví ukazatel další u prvku předchozího aktuálnímu
     *     na nový prvek
     *     <li> nastaví ukazatel předchozí u aktuálního na nový prvek
     *     </ol>
     * <li> pokud prvek předchozí aktuálního je {@code null}, tak:
     *      <ol>
     *      <li> nastaví hlavičku na prvek předchozí aktuálnímu, který
     *      předtím bude nastaven na nový prvek
     *      </ol>
     * <li> zvyší počet prvků o jedničku
     * </ol>
     *
     * @see AbstrDoubleList#zvysMohutnost()
     */
    @Override
    public void vlozPredchudce(T data) throws DoubleListException {
        pozadatNeNull(data);
        pozadatAktualni();

        final Prvek<T> novyPrvek = new Prvek<>(data);
        novyPrvek.dalsi = aktualni;
        novyPrvek.predchozi = aktualni.predchozi;
        if (aktualni.predchozi != null) {
            aktualni.predchozi.dalsi = novyPrvek;
            aktualni.predchozi = novyPrvek;
        } else {
            hlavicka = aktualni.predchozi = novyPrvek;
        }
        zvysMohutnost();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T odeberAktualni()">
    /**
     * Zavolá příslušnou metodu pro odstranění prvního/posledního prvku ze seznamu, pokud je aktuální prvek
     * zároveň prvním/posledním prvkem. Pokud ale není, přepojí odkazy tak, aby přeskočily aktuální prvek
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ošetří, zda není seznam prázdný
     * <li> Ošetří, zda je nastaven aktuální prvek
     * <li> Pokud aktuální je zároveň hlavočkou, tak:
     *     <ol>
     *     <li> Vrátí návratový prvek z metody {@link AbstrDoubleList#odeberPrvni()}
     *     </ol>
     * <li> Pokud aktuální je zároveň patičkou, tak:
     *     <ol>
     *     <li> Vrátí návratový prvek z metody {@link AbstrDoubleList#odeberPosledni()}
     *     </ol>
     * <li> Vytvoří nový prvek uchovávahící odkaz na aktuální prvek
     * <li> Nastaví okazatel další u prvku předchozího aktuálnímu na prvek další
     * aktuálnímu
     * <li> Nastaví předchozí ukazatel u prvku další aktuálního na prvel předchozí
     * aktuálnímu
     * <li> Vynyluje ukazatel aktuální
     * <li> Sníží počet prvků v seznamu o jedničku
     * <li> Vrátí položku aktuálního prvky, který byl uchovávan před mazáním
     * </ol>
     *
     * @see AbstrDoubleList#snizMohutnost()
     */
    @Override
    public T odeberAktualni() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();

        if (aktualni == hlavicka)
            return odeberPrvni();
        else if (aktualni == paticka)
            return odeberPosledni();

        final Prvek<T> odebranyPrvek = aktualni;
        aktualni.predchozi.dalsi = aktualni.dalsi;
        aktualni.dalsi.predchozi = aktualni.predchozi;
        aktualni = null;
        snizMohutnost();
        return odebranyPrvek.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T odeberPrvni()">
    /**
     * Nastaví hlavicku i paticku seznamu na {@code null}, pokud hlavička je jedinným prvkem v celém seznamu, čímž
     * provede odstranění hlavičky (tj. všech prvků) ze struktury. Jestliže po hlavičce jsou další prvky, provede
     * nastavení druhého prvku na hlavičku a aktualizuje ukazatel na předchozí uzel u této nové hlavičky
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ověří, zda je seznam prázdný
     * <li> Pokud hlavička je zároveň nastavena jako aktuální, tak:
     *     <ol>
     *     <li> Nastaví ukazatel aktuální na hodnotu {@code null},
     *     protože následně hlavička bude smazaná
     *     </ol>
     * <li> Vytvoří nový prvek, který uchovává hlavičky, aby metoda
     * následně mohla vrátit její hodnotu
     * <li> Pokud prvek další hlavičky je {@code null}, tak:
     *     <ol>
     *     <li> Nastaví hlavičku na patičku, která předtím bude
     *     vynulována
     *     <li> Vynuluje počet prvků s seznamu
     *     <li> Vrátí hodnotu hlavičky a ukončí metodu
     *     </ol>
     * <li> Nastaví hlavičku na prvek další hlavičky
     * <li> Nastaví prvek předchozí hlavičce na {@code null}
     * <li> Sníží počet prvků v seznamu o jedničku
     * <li> Vrátí hodnotu hlavičky a ukončí metodu
     * </ol>
     *
     * @see AbstrDoubleList#vynulujMohutnost()
     * @see AbstrDoubleList#snizMohutnost()
     */
    @Override
    public T odeberPrvni() throws DoubleListException {
        pozadatNePrazdy();

        if (hlavicka == aktualni) {
            aktualni = null;
        }
        final Prvek<T> odebranyPrvek = hlavicka;
        if (hlavicka.dalsi == null) {
            hlavicka = paticka = null;
            vynulujMohutnost();
            return odebranyPrvek.polozka;
        }
        hlavicka = hlavicka.dalsi;
        hlavicka.predchozi = null;
        snizMohutnost();
        return odebranyPrvek.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T odeberPosledni()">
    /**
     * Nastaví hlavicku i paticku seznamu na {@code null}, pokud patička je jedinným prvkem v celém seznamu, čímž
     * provede odstranění patičky (tj. všech prvků) ze struktury. Jestliže před patičkou jsou další prvky, provede
     * nastavení předposledního prvku na patičku a aktualizuje ukazatel na další uzel u této nové patičky
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ověří, zda je seznam prázdný
     * <li> Pokud patička je zároveň nastavena jako aktuální, tak:
     *     <ol>
     *     <li> Nastaví ukazatel aktuální na hodnotu {@code null},
     *     protože následně patička bude smazaná
     *     </ol>
     * <li> Vytvoří nový prvek, který uchovává patičku, aby metoda
     * následně vrátila tuto hodnotu
     * <li> Pokud prvek předchozí patičky je {@code null}, tak:
     *     <ol>
     *     <li> Nastaví hlavičku na patičku, která předtím bude
     *     vynulována
     *     <li> Vynuluje počet prvků s seznamu
     *     <li> Vrátí hodnotu patičky a ukončí metodu
     *     </ol>
     * <li> Vastaví patičku na prvek předchozí patičce
     * <li> Vastaví prvek další hlavičky na {@code null}
     * <li> Sníží počet prvků v seznamu o jedničku
     * <li> Vratí hodnotu patičky a ukončí metodu
     * </ol>
     *
     * @see AbstrDoubleList#vynulujMohutnost()
     * @see AbstrDoubleList#snizMohutnost()
     */
    @Override
    public T odeberPosledni() throws DoubleListException {
        pozadatNePrazdy();

        if (paticka == aktualni) {
            aktualni = null;
        }
        final Prvek<T> odebranyPrvek = paticka;
        if (paticka.predchozi == null) {
            hlavicka = paticka = null;
            vynulujMohutnost();
            return odebranyPrvek.polozka;
        }
        paticka = paticka.predchozi;
        paticka.dalsi = null;
        snizMohutnost();
        return odebranyPrvek.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T odeberNaslednika()">
    /**
     * Zavolá metodu pro odstranění posledního prvku ze seznamu, pokud další prvek za aktuálním je poslední.
     * Jestliže následující prvek za aktuálním není poslední, provede aktualizaci ukazatelů u aktuálního
     * prvku {@code (aktualni.dalsi)} a u prvku sledujícím za následníkem aktuálního {@code (aktualni.dalsi.dalsi.predchozi)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ověří, zda není seznam prázdný
     * <li> Ověrí, zda je nastaven aktuální prvek
     * <li> Pokud aktuální prvek je zároveň patičkou, tak:
     *     <ol>
     *     <li> Vyhodí výjimku a ukončí metodu
     *     </ol>
     * <li> Vytvoří nový prvek obsahující prvek, který je
     * potřeba odebrat (tj. prvek další aktuálního)
     * <li> Pokud prvek, který musí být odebrán (tj. prvek
     * další aktuálního), je zároveň patičkou, tak:
     *     <ol>
     *     <li> Vyvolá metodu {@link AbstrDoubleList#odeberPosledni()}
     *     a vratí hodnotu posledního prvku, tím pak ukončí tuto metodu
     *     </ol>
     * <li> Nastavení ukazatele předchozí u prvku, který je další, než
     * prvek, který bude odebrán:
     *     <ol>
     *     <li> {@code aktualni.dalsi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.dalsi.dalsi} - prvek, který je další,
     *     než prvek z bodu (1)
     *     <li> {@code aktualni.dalsi.dalsi.predchozi} - ukazatel
     *     předchozí u prvku z bodu (2)
     *     <li> {@code aktualni.dalsi.predchozi} - ukazatel předchozí u
     *     prvku z bodu (1)
     *     </ol>
     * <li> Nastavení ukazatele další u aktuálního prvku (tj. prvku, který
     * je předchozí tomu, který je potřeba odebrat):
     *     <ol>
     *     <li> {@code aktualni.dalsi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.dalsi.predchozi} - prvek, který je
     *     předchozí, než prvek z bodu (1)
     *     <li> {@code aktualni.dalsi.predchozi.dalsi} - ukazatel
     *     další u prvku z bodu (2)
     *     <li> {@code aktualni.dalsi.predchozi} - ukazatel další u
     *     prvku z bodu (1)
     *     </ol>
     * <li> Sníží počet prvků v seznamu o jedničku
     * <li> Vrátí hodnotu odebraného prvku a tím ukončí metodu
     * </ol>
     *
     * @see AbstrDoubleList#odeberPosledni()
     * @see AbstrDoubleList#snizMohutnost()
     */
    @Override
    public T odeberNaslednika() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();

        if (aktualni == paticka)
            throw new DoubleListException(
                    DoubleListZprava.NENI_DALSI_PRVEK.zprava());

        final Prvek<T> odebranyPrvek = aktualni.dalsi;
        if (aktualni.dalsi == paticka) {
            return odeberPosledni();
        }
        aktualni.dalsi.dalsi.predchozi = aktualni;
        aktualni.dalsi = aktualni.dalsi.dalsi;
        snizMohutnost();
        return odebranyPrvek.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T odeberPredchudce()">
    /**
     * Zavolá metodu pro odstranění prvního prvku ze seznamu, pokud předchozí prvek před aktuálním je prvním.
     * Jestliže předchozí prvek před aktuálním není první, provede aktualizaci ukazatelů u aktuálního
     * prvku {@code (aktualni.predchozi)} a u prvku sledujícím před předchůdcem aktuálního
     * {@code (aktualni.predchozi.predchozi.dalsi)}
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Ověří, zda není seznam prázdný
     * <li> Ověrí, zda je nastaven aktuální prvek
     * <li> Pokud aktuální prvek je zároveň hlavičkou, tak:
     *     <ol>
     *     <li> Vyhodí výjimku a ukončí metodu
     *     </ol>
     * <li> Vytvoří nový prvek obsahující prvek, který je
     * potřeba odebrat (tj. prvek předchozí aktuálnímu)
     * <li> Pokud prvek, který musí být odebrán (tj. prvek
     * předchozí aktuálnímu), je zároveň hlavičkou, tak:
     *     <ol>
     *     <li> Vyvolá metodu {@link AbstrDoubleList#odeberPrvni()}
     *     a vratí hodnotu prvního prvku, tím pak ukončí tuto metodu
     *     </ol>
     * <li> Nastavení ukazatele další u prvku, který je předchozí
     * prvku, který bude odebrán:
     *     <ol>
     *     <li> {@code aktualni.predchozi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.predchozi.predchozi} - prvek, který je
     *     předchozí prvku z bodu (1)
     *     <li> {@code aktualni.predchozi.predchozi.dalsi} - ukazatel další
     *     u prvku z bodu (2)
     *     <li> {@code aktualni.predchozi.dalsi} - ukazatel další u
     *     prvku z bodu (1)
     *     </ol>
     * <li> Nastavení ukazatele předchozí u aktuálního prvku (tj. prvku, který
     * je další, než prvek, který je potřeba odebrat):
     *     <ol>
     *     <li> {@code aktualni.predchozi} - prvek, který je potřeba odebrat
     *     <li> {@code aktualni.predchozi.dalsi} - prvek, který je
     *     další, než prvek z bodu (1)
     *     <li> {@code aktualni.predchozi.dalsi.predchozi} - ukazatel
     *     předchozí u prvku z bodu (2)
     *     <li> {@code aktualni.predchozi.predchozi} - ukazatel předchozí u
     *     prvku z bodu (1)
     *     </ol>
     * <li> Sníží počet prvků v seznamu o jedničku
     * <li> Vrátí hodnotu odebraného prvku a tím ukončí metodu
     * </ol>
     *
     * @see AbstrDoubleList#odeberPrvni()
     * @see AbstrDoubleList#snizMohutnost()
     */
    @Override
    public T odeberPredchudce() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();

        if (aktualni == hlavicka)
            throw new DoubleListException(
                    DoubleListZprava.NENI_PREDCHOZI_PRVEK.zprava());

        final Prvek<T> odebranyPrvek = aktualni.predchozi;
        if (aktualni.predchozi == hlavicka) {
            return odeberPrvni();
        }
        aktualni.predchozi.predchozi.dalsi = aktualni;
        aktualni.predchozi = aktualni.predchozi.predchozi;
        snizMohutnost();
        return odebranyPrvek.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T zpristupniAktualni()">
    /**
     * Vrátí hodnotu aktuálního prvku seznamu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Pokud není seznam prázdný, a zároveň aktuálně nastavený prvek je nastaven
     * (obsahuje nějakou hodnotu), tak:
     *      <ol>
     *      <li> Vytvoří nový uzel, obsahující odkaz na aktuální prvek seznamu
     *      <li> Nastaví aktuální ukazatel na další prvek toho aktuálního
     *      <li> Vrátí položku prvku, který byl aktuálním před bodem (2)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniAktualni() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();
        return aktualni.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T zpristupniPrvni()">
    /**
     * Nastaví aktuální ukazatel na hlavičku a zároveň vrátí tuto hodnotu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Pokud není seznam prázdný (je alespoň jeden prvek), tak:
     *      <ol>
     *      <li> Nastaví aktuální ukazatel na hlavičku
     *      <li> Vrátí položku prvního (teď už aktuálního) prvku
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPrvni() throws DoubleListException {
        pozadatNePrazdy();

        aktualni = hlavicka;
        return aktualni.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T zpristupniPosledni()">
    /**
     * Nastaví aktuální ukazatel na patičku a zároveň vrátí tuto hodnotu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Pokud není seznam prázdný (je alespoň jeden prvek), tak:
     *      <ol>
     *      <li> Nastaví aktuální ukazatel na patičku
     *      <li> Vrátí položku posledního (teď už aktuálního) prvku
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPosledni() throws DoubleListException {
        pozadatNePrazdy();

        aktualni = paticka;
        return aktualni.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T zpristupniNaslednika()">
    /**
     * Nastaví aktuální ukazatel na následníka a zároveň vrátí tuto hodnotu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Pokud není seznam prázdný (je alespoň jeden prvek) a zároveň je nastaven aktuální ukazatel, tak:
     *      <ol>
     *      <li> Nastaví aktuální ukazatel na prvek další aktuálnímu
     *      <li> Vrátí položku prvku, který je další než aktuální (teď už aktuální)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniNaslednika() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();

        aktualni = aktualni.dalsi;
        return aktualni.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: T zpristupniPredchudce()">
    /**
     * Nastaví aktuální ukazatel na předchůdce a zároveň vrátí tuto hodnotu
     *
     * <p> Popis logiky:
     * <ol>
     * <li> Pokud není seznam prázdný (je alespoň jeden prvek) a zároveň je nastaven aktuální ukazatel, tak:
     *      <ol>
     *      <li> Nastaví aktuální ukazatel na prvek předchozí aktuálnímu
     *      <li> Vrátí položku prvku, který je předchozí než aktuální (teď už aktuální)
     *      </ol>
     * </ol>
     */
    @Override
    public T zpristupniPredchudce() throws DoubleListException {
        pozadatNePrazdy();
        pozadatAktualni();

        aktualni = aktualni.predchozi;
        return aktualni.polozka;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody: Aktualizace mohutnosti">
    /**
     * Zvýší hodnotu atributu {@code pocet} o {@code 1}
     */
    private void zvysMohutnost() { mohutnost++; }

    /**
     * Sníží hodnotu atributu {@code pocet} o {@code 1}
     */
    private void snizMohutnost() { mohutnost--; }

    /**
     * Vynuluje atribut {@code pocet}, tzn. aktuální počet prvků se bude rovnat {@code 0}
     */
    private void vynulujMohutnost() { mohutnost = NULTA_HODNOTA; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody: Žádosti na null/prázdnost">
    /**
     * Zjišťuje, zda jsou vstupní data {@code null}
     *
     * @throws DoubleListException Když jsou data {@code null}
     */
    private void pozadatNeNull(T data) throws DoubleListException {
        if (data == null)
            throw new DoubleListException(
                    DoubleListZprava.NULL_VSTUPNI_DATA.zprava());
    }

    /**
     * Zjišťuje, zda je alespoň jeden prvek v seznamu
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    private void pozadatNePrazdy() throws DoubleListException {
        if (jePrazdny())
            throw new DoubleListException(
                    DoubleListZprava.PRAZDNY_SEZNAM.zprava());
    }

    /**
     * Zjišťuje, zda aktuálně nastavený prvek není nastaven
     *
     * @throws DoubleListException Když je aktuálně nastavený prvek {@code null}
     */
    private void pozadatAktualni() throws DoubleListException {
        if (!jeAktualni())
            throw new DoubleListException(
                    DoubleListZprava.NENI_AKTUALNI_UKAZATEL.zprava());
    }

    /**
     * Zjišťuje, zda je aktuální prvek nastaven
     *
     * @return {@code true}, pokud aktuální prvek má nějakou hodnotu, jinak {@code false}
     */
    private boolean jeAktualni() { return aktualni != null; }
// </editor-fold>
}
