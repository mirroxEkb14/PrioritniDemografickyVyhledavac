package cz.upce.fei.bdats.sekvence.lifo;

import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.LifoException;

import java.util.Stack;

/**
 * Rozhraní definuje sadu základních operací abstraktní datové struktury <i>(ADS)</i> <b>zásobník</b> <i>(eng.
 * Stack)</i>, což je jednou z variací lineárních seznamů splňující strategii LIFO <i>(Last In, First Out - poslední
 * dovnitř, první ven)</i>
 *
 * <p> Zásobník je postaven nad ADS <b>obousměrně necyklický zřetězený lineární seznam</b>
 *
 * <p> Vkládání prvků se provádí pouze na začátek, naopak odebírání prvků se provádí pouze ze začátku, což je
 * jedinnou možnou bránou struktury pro realizaci vkládání a odebírání prvků
 *
 * <p> Zpřístupňování se provádí pouze pro jeden prvek nacházející se na vrcholu struktury
 *
 * @see IAbstrDoubleList
 * @see Stack
 *
 * @param <T> Typ prvků, které budou uloženy ve struktuře
 */
public interface IAbstrLifo<T> {

    /**
     * Destruktor - <i>clear()</i>
     * <p>
     * Zrušení celého zásobníku
     */
    void zrus();

    /**
     * Zjišťovací operace - <i>empty()</i>
     * <p>
     * Test prázdnosti zásobníku
     *
     * @return {@code true}, pokud je zásobník prázdný, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Dotaz na počet prvků - <i>size()</i>
     * <p>
     * Vrací počet prvků v zásobníku
     *
     * @return Číslo prvků
     */
    int mohutnost();

    /**
     * Dynamická operace - vkládací metoda - <i>push(E item)</i>
     * <p>
     * Vložení prvku do zásobníku na jeho vrchol <i>(eng. top)</i>
     *
     * @param data Datová jednotka typu {@code T}
     *
     * @throws LifoException Když jsou vstupní data {@code null}
     */
    void vlozNaZacatek(T data) throws LifoException;

    /**
     * Dynamická operace - odebírací metoda - <i>pop()</i>
     * <p>
     * Odebrání prvního prvku z vrcholu zásobníku
     *
     * @return Prvek z vrcholu zásobníku
     *
     * @throws LifoException Když je zásobník prázdný
     */
    T odeberZeZacatku() throws LifoException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda - <i>peek()</i>
     * <p>
     * Zpřístupnění vrcholu zásobníku (bez jeho odebrání)
     *
     * @return Prvek z vrcholu zásobníku
     *
     * @throws LifoException Když je zásobník prázdný
     */
    T zpristupniZacatek() throws LifoException;
}
