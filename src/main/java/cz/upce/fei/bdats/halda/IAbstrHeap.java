package cz.upce.fei.bdats.halda;

import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.HeapException;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Rozhraní definuje sadu základních operací abstraktní datové struktury <i>(ADS)</i> <b>prioritní fronta</b>
 * <i>(eng. PriorityQueue)</i>, což je jednou z implementací množiny s lineárním uspořádáním, přičemž uspořádání
 * je určéváno prioritami prvků vzhledem k jejich pořadí odebírání ze struktury, tzn. každý prvek je charakterizován
 * prioritou mající vliv na pořadí vybírání prvků z prioritní fronty
 *
 * <p> Prioritní fronta rozšiřuje binární vyhledávací strom <i>(BVS)</i> (což je tabulkou na binárním stromu),
 * čímž zajišťuje implementaci na levostranné hadlě na poli, což je binárním stromem, pro něhož platí, že každý jeho
 * vrchol má prioritu větší než priority obou jeho synů (pokud existují)
 *
 * @see PriorityQueue
 *
 * @param <E> Typ prvků prioritní fronty, které musí být porovnatelné podle určité priority
 */
public interface IAbstrHeap<E> {

    /**
     * Vybuduje požadovanou prioritní frontu podle vstupního pole
     *
     * @param pole Pole prvků predstavující implementaci haldy
     *
     * @throws HeapException Když jsou vstupní data {@code null}
     */
    void vybuduj(E[] pole) throws HeapException;

    /**
     * Přebuduje prioritní frontu dle požadované priority
     *
     * @param komp Komparátor reprezentující kritérium priority
     *
     * @throws HeapException Když jsou vstupní data {@code null}
     */
    void reorganizuj(Comparator<E> komp) throws HeapException;

    /**
     * Destruktor - <i>clear()</i>
     * <p>
     * Zrušení celé prioritní fronty
     */
    void zrus();

    /**
     * Zjišťovací operace - <i>isEmpty()</i>
     * <p>
     * Test naplněnosti prioritní fronty
     *
     * @return {@code true}, pokud je prioritní fronta prázdná, jinak {@code false}
     */
    boolean jePrazdna();

    /**
     * Dotaz na počet prvků - <i>size()</i>
     * <p>
     * Vrací počet prvků v prioritní frontě
     *
     * @return Hodnota počtu prvků
     */
    int mohutnost();

    /**
     * Dynamická operace - vkládací metoda - <i>add(E e)</i>
     * <p>
     * Vložení prvku do prioritní fronty
     *
     * @param prvek Datová entita typu {@code E}
     *
     * @throws HeapException Když jsou vstupní data {@code null}
     */
    void vloz(E prvek) throws HeapException;

    /**
     * Dynamická operace - odebírací metoda - <i>poll()</i>
     * <p>
     * odebraní prvku z prioritní fronty s maximální prioritou (tj. z hlavičky)
     *
     * @return Odkaz na odebraný prvek
     *
     * @throws HeapException Když je prioritní fronta prázdná
     */
    E odeberMax() throws HeapException;

    /**
     * Operace typu <i>Selektor</i> - přístupová metoda - <i>peek()</i>
     * <p>
     * Zpřístupnění prvku z prioritní fronty s maximální prioritou (tj. z hlavičky)
     *
     * @return Odkaz na prvek z hlavičky fronty
     *
     * @throws HeapException Když je prioritní fronta prázdná
     */
    E zpristupniMax() throws HeapException;

    /**
     * Vypsání prvků prioritní fronty (s využitím iterátoru do šířky i do hloubky)
     *
     * @param typ Typ prohlížení pro iterátor
     *
     * @return Textový řetězec popisující strukturu prioritní fronty podle požadovaného typu
     *
     * @throws HeapException Když jsou vstupní data {@code null}
     */
    String vypis(ETypProhl typ) throws HeapException;
}
