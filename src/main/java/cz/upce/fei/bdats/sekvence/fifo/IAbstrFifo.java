package cz.upce.fei.bdats.sekvence.fifo;

import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.FifoException;

import java.util.Queue;

/**
 * Rozhraní definuje sadu základních operací abstraktní datové struktury <i>(ADS)</i> <b>fronta</b> <i>(eng.
 * Queue)</i>, což je jednou z variací lineárních seznamů vystihující princip FIFO <i>(First In, First Out - první
 * dovnitř, první ven)</i>, tzn. v jakém pořadí byly prvky vloženy, ve stejném pořadí budou odebrány
 *
 * <p> Fronta je postavena nad ADS <b>obousměrně necyklický zřetězený lineární seznam</b>
 *
 * <p> Vkládání prvků se provádí pouze na konec, naopak odebírání prvků se provádí pouze ze začátku, tzn. lineární
 * DS disponuje jednou vstupní, resp. výstupní bránou umožňující vkládání, resp. odebírání prvků ze struktury
 *
 * <p> Zpřístupňování se provádí pouze pro dva prvky nacházející se na začátku, resp. na konci struktury
 *
 * @see IAbstrDoubleList
 * @see Queue
 *
 * @param <T> Typ prvků, které budou uloženy ve struktuře
 */
public interface IAbstrFifo<T> {

    /**
     * Destruktor - <i>clear()</i>
     * <p>
     * Zrušení celé fronty
     */
    void zrus();

    /**
     * Zjišťovací operace - <i>isEmpty()</i>
     * <p>
     * Test prázdnosti fronty
     *
     * @return {@code true}, pokud je fronta prázdná, jinak {@code false}
     */
    boolean jePrazdna();

    /**
     * Dotaz na počet prvků - <i>size()</i>
     * <p>
     * Vrací počet prvků ve frontě
     *
     * @return Číslo prvků
     */
    int mohutnost();

    /**
     * Dynamická operace - vkládací metoda - <i>add(E e)</i>
     * <p>
     * Vložení prvku do fronty na jeho konec <i>(eng. tail)</i>
     *
     * @param data Datová jednotka typu {@code T}
     *
     * @throws FifoException Když jsou vstupní data {@code null}
     */
    void vlozNaKonec(T data) throws FifoException;

    /**
     * Dynamická operace - odebírací metoda - <i>remove()</i>
     * <p>
     * Odebrání prvku ze začátku fronty <i>(eng. head)</i>
     *
     * @return Prvek z hlavičky fronty
     *
     * @throws FifoException Když je franta prázdná
     */
    T odeberZeZacatku() throws FifoException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda - <i>peek()</i>
     * <p>
     * Zpřístupnění hlavičky fronty (bez její odebrání)
     *
     * @return Prvek z hlavičky fronty
     *
     * @throws FifoException Když je fronta prázdná
     */
    T zpristupniZacatek() throws FifoException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda
     * <p>
     * Zpřístupnění patičky fronty (bez její odebrání)
     *
     * @return Prvek z patičky fronty
     *
     * @throws FifoException Když je fronta prázdná
     */
    T zpristupniKonec() throws FifoException;
}
