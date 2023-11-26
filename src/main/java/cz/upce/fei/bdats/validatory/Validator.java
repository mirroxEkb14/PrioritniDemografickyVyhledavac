package cz.upce.fei.bdats.validatory;

import org.jetbrains.annotations.NotNull;

/**
 * Funkcionální rozhraní poskytuje abstrakci pro validační logiku. Implementace tohoto rozhraní umožňuje
 * definovat různá validační pravidla pro různé typy vstupů - {@link String}, {@link Integer} etc.
 *
 * @param <T> Typ vstupních hodnot, které mají být validovány
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Ověřuje platnost vstupu podle předem definovaných validačních pravidel (implementace této metody)
     *
     * @param vstup Hodnota, která má být validována
     *
     * @return {@code true}, pokud je hodnota platná podle požadované logiky, jinak {@code false}
     */
    boolean jeValidni(T vstup);

    /**
     * Výchozí metoda pro validaci volající {@link Validator#jeValidni(T)}
     *
     * <p> Metoda přijímá pole hodnot typu {@code T} a cyklicky volá {@link #jeValidni(T)}, aby ověřila platnost
     * každé hodnoty. Pokud je alespoň jedna hodnota neplatná, vrátí {@code false}, jinak {@code true}
     *
     * <p> Varargs (variable-length argument list) - variadický argument je funkce umožňující definovat proměnný
     * počet argumentů daného typu pro metodu
     *
     * @param vstup Pole hodnot typu {@code T} k validaci
     *
     * @return {@code true}, pokud jsou všechny hodnoty platné, jinak {@code false}
     */
    default boolean validuj(T @NotNull ... vstup) {
        for (T hodnota : vstup) {
            if (!jeValidni(hodnota))
                return false;
        }
        return true;
    }
}
