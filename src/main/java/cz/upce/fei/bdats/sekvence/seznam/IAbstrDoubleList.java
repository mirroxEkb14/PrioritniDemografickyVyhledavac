package cz.upce.fei.bdats.sekvence.seznam;

import cz.upce.fei.bdats.vyjimky.DoubleListException;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Rozhraní deklaruje sadu zádladních operací abstraktní datové struktury <i>(ADS)</i> <b>obousměrně necyklický
 * zřetězený lineární seznam</b> v dynamické paměti <i>(eng. Doubly Linked List)</i>, což je jedním z
 * reprezentantů <b>lineárně uspořádané množiny</b> <i>(Super ADT Sekvence)</i>
 *
 * <p> Implementuje implicitní rozhraní {@link Iterable}
 *
 * @param <T> Předem neznámý generický typový parametr, jenž následně bude nahrazen konkrétní třídou/rozhraním
 */
public interface IAbstrDoubleList<T> extends Iterable<T> {

    /**
     * Destruktor
     * <p>
     * Zrušení celého seznamu
     */
    void zrus();

    /**
     * Zjišťovací operace
     * <p>
     * Test naplněnosti seznamu
     *
     * @return {@code true}, pokud je seznam prázdný, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Dotaz na počet prvků
     * <p>
     * Vrací aktuální počet dat v seznamu
     *
     * @return Hodnota s počtem dat v seznamu
     */
    int mohutnost();

    /**
     * Operace typu <i>Prohlídka</i>
     * <p>
     * Vytvoří iterátor dle rozhraní {@link Iterable} pro procházení položek objektů seznamu
     *
     * @return Iterátor nad prvky typu {@code T}
     */
    Iterator<T> iterator();

    /**
     * Dynamická operace - vkládací metoda
     * <p>
     * Vložení prvku do seznamu na první místo
     *
     * @param data Datová entita typu {@code T}
     *
     * @throws DoubleListException Když jsou vstupní data {@code null}
     */
    void vlozPrvni(T data) throws DoubleListException;

    /**
     * Dynamická operace - vkládací metoda
     * <p>
     * Vložení prvku do seznamu na poslední místo
     *
     * @param data Datová entita typu {@code T}
     *
     * @throws DoubleListException Když jsou vstupní data {@code null}
     */
    void vlozPosledni(T data) throws DoubleListException;

    /**
     * Dynamická operace - vkládací metoda
     * <p>
     * Vložení prvku do seznamu jakožto následníka aktuálního prvku
     *
     * @param data Datová entita typu {@code T}
     *
     * @throws DoubleListException Když jsou vstupní data {@code null}, anebo není nastaven aktuální prvek
     */
    void vlozNaslednika(T data) throws DoubleListException;

    /**
     * Dynamická operace - vkládací metoda
     * <p>
     * Vložení prvku do seznamu jakožto předchůdce aktuálního prvku
     *
     * @param data Datová entita typu {@code T}
     *
     * @throws DoubleListException Když jsou vstupní data {@code null}, anebo není nastaven aktuální prvek
     */
    void vlozPredchudce(T data) throws DoubleListException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebrání (vyjmutí) aktuálního prvku ze seznamu poté je aktuální prvek nastaven na první prvek
     *
     * @return Odkaz na odebíraný objekt, datovou entitu typu {@code T}
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    T odeberAktualni() throws DoubleListException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebrání prvního prvku ze seznamu
     *
     * @return Odkaz na odebíraný objekt, datovou entitu typu {@code T}
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    T odeberPrvni() throws DoubleListException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebrání posledního prvku ze seznamu
     *
     * @return Odkaz na odebíraný objekt, datovou entitu typu {@code T}
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    T odeberPosledni() throws DoubleListException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebrání následníka aktuálního prvku ze seznamu
     *
     * @return Odkaz na odebíraný objekt, datovou entitu typu {@code T}
     *
     * @throws DoubleListException Když je seznam prázdný, anebo není nastaven aktuální prvek
     */
    T odeberNaslednika() throws DoubleListException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebrání předchůdce aktuálního prvku ze seznamu
     *
     * @return Odkaz na odebíraný objekt, datovou entitu typu {@code T}
     *
     * @throws DoubleListException Když je seznam prázdný, anebo není nastaven aktuální prvek
     */
    T odeberPredchudce() throws DoubleListException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění aktuálního prvku seznamu: vrací hodnotu aktuálního prvku a
     * přestaví vnitřní aktuální ukazatel na další prvek seznamu
     *
     * @return Odkaz na object/datovou entitu typu {@code T} z aktuálního prvku seznamu
     *
     * @throws DoubleListException Když je seznam prázdný, anebo není nastaven aktuální prvek
     */
    T zpristupniAktualni() throws DoubleListException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění prvního prvku seznamu: vrací hodnotu prvního prvku
     *
     * @return Odkaz na object/datovou entitu typu {@code T} z prvního prvku seznamu
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    T zpristupniPrvni() throws DoubleListException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění posledního prvku seznamu: vrací hodnotu posledního prvku
     *
     * @return Odkaz na objekt/datovou entitu typu {@code T} z posledního prvku seynamu
     *
     * @throws DoubleListException Když je seznam prázdný
     */
    T zpristupniPosledni() throws DoubleListException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění následníka aktuálního prvku
     *
     * @return Odkaz na object/datovou entitu typu {@code T} za aktuálním prvkem seznamu
     *
     * @throws DoubleListException Když je seznam prázdný, anebo není nastaven aktuální prvek
     */
    T zpristupniNaslednika() throws DoubleListException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění předchůdce aktuálního prvku
     *
     * @return Odkaz na object/datovou entitu typu {@code T} před aktuálním prvkem seznamu
     *
     * @throws DoubleListException Když je seznam prázdný, anebo není nastaven aktuální prvek
     */
    T zpristupniPredchudce() throws DoubleListException;

    /**
     * Iterace přes sekvenci prvků
     * <p>
     * Převede obsah seznamu na datový proud, který předá při návratu
     *
     * <p> <b>Poznámka:</b> V implementačních třídách se nepřekrývá
     *
     * @return Datovy proud
     */
    default Stream<T> stream() { return StreamSupport.stream(spliterator(), false); }
}
