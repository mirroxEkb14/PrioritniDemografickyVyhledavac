package cz.upce.fei.bdats.strom;

import cz.upce.fei.bdats.vyjimky.StromException;

import java.util.Iterator;

/**
 * Rozhraní definuje sadu základních operací abstraktní datové struktury <i>(ADS)</i> <b>binární vyhledávací
 * strom</b> <i>(eng. Binary Search Tree)</i>, což je jedním z reprezentantů <b>hierarchicky uspořádané množiny</b>
 * <i>(Super ADT Hierarchie)</i>
 *
 * <p> ADS <b>dynamický binární strom</b> reprezentuje organizaci s explicitními vztahy a je orientovaný směrem
 * od kořene k listům
 *
 * <p> Zpřístupňovací operace nejsou základními
 *
 * <p> Prohlídka využívá iterátor <i>do šířky</i> a <i>do hloubky (in-order)</i>
 *
 * @param <K> Typ klíčů v tabulce, které musí být porovnatelné
 * @param <V> Typ hodnot v tabulce
 */
public interface IAbstrTable<K extends Comparable<K>, V> {

    /**
     * Destruktor
     * <p>
     * Zrušení celé tabulky
     */
    void zrus();

    /**
     * Zjišťovací operace
     * <p>
     * Test prázdnosti tabulky
     *
     * @return {@code true}, pokud je tabulka prázdná, jinak {@code false}
     */
    boolean jePrazdny();

    /**
     * Dotaz na počet prvků
     * <p>
     * Vrátí počet prvků v binárním stromu
     *
     * @return Číslo prvků
     */
    int mohutnost();

    /**
     * Prohlídka
     * <p>
     * Vytvoří iterátor umožňující procházení stromu do šířky/hloubky (in-order)
     *
     * @param typ Typ prohlížení, buďto do šířky, nebo do hloubky (in-order)
     *
     * @return Instance iterátoru
     */
    Iterator<V> vytvorIterator(ETypProhl typ);

    /**
     * Vyhledá prvek dle klíče
     *
     * @param klic Klíč, podle něhož se vyhledává prvek
     *
     * @return Hodnota přiřazená k zadanému klíči
     *
     * @throws StromException Když klíč není platný (tj. prvek s daným klíčem není nalezen)
     */
    V najdi(K klic) throws StromException;

    /**
     * Dynamická operace - vkládací metoda
     * <p>
     * Vloží prvek do tabulky
     *
     * @param klic Klíč, pod němž se má prvek vložit
     * @param hodnota Prvek, jenž se má vložit do tabulky
     *
     * @throws StromException Když daný klíč je {@code null}
     */
    void vloz(K klic, V hodnota) throws StromException;

    /**
     * Dynamická operace - odebírací metoda
     * <p>
     * Odebere prvek dle klíče z tabulky
     *
     * @param klic Klíč, pod němž se má prvek odebrat
     *
     * @return Odebraná hodnota
     *
     * @throws StromException Když klíč není platný (tj. prvek s daným klíčem není nalezen),
     * anebo je strom prázdný
     */
    V odeber(K klic) throws StromException;

    /**
     * Vypsání prvků binárního stromu (s využitím iterátoru do šířky i do hloubky)
     *
     * <p> <b>Příklad</b>:
     * <ul>
     * <li> posloupnost přidání: e, c, d, a, b, f, h, g, i
     * <li> posloupnost výpisu do šířky: e, c, f, a, d, h, b, g, i
     * <li> posloupnost výpisu do hloubky (in-order): a, b, c, d, e, f, g, h, i
     * </ul>
     *      e
     *    /   \
     *   c     f
     *  / \     \
     * a   d     h
     *  \       / \
     *   b     g   i
     *
     * @param typ Typ prohlížení
     *
     * @return Textový řetězec popisující strukturu binárního stromu podle požadovaného typu iterátoru
     */
    String vypis(ETypProhl typ);
}
