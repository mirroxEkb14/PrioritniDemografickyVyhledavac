package cz.upce.fei.bdats.validatory;

import org.jetbrains.annotations.NotNull;

/**
 * Funkcionální rozhraní definuje sadu základních operací pro validaci vstupních da
 *
 * @param <T> Typ vstupních hodnot, jež mají být validovány
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Ověřuje platnost vstupu podle předem definovaných validačních pravidel (implementace této metody)
     *
     * @param vstup Hodnota, jež má být validována
     *
     * @return {@code true} pokud je hodnota platná, jinak {@code false}
     */
    boolean jeValidni(T vstup);

    /**
     * Volá cyklicky {@link #jeValidni(T)} ověřující platnost každé hodnoty. Pokud je alespoň jedna hodnota
     * neplatná, vrátí {@code false}, jinak {@code true}
     *
     * <p> <b>Varargs</b> <i>(eng. variable-length argument list)</i> - variadický argument je funkce umožňující definovat proměnný
     * počet argumentů daného typu pro metodu
     *
     * @param vstup Pole hodnot typu {@code T} k validaci
     *
     * @return {@code true} pokud jsou všechny hodnoty platné, jinak {@code false}
     */
    default boolean validuj(T @NotNull ... vstup) {
        for (T hodnota : vstup) {
            if (!jeValidni(hodnota))
                return false;
        }
        return true;
    }
}
