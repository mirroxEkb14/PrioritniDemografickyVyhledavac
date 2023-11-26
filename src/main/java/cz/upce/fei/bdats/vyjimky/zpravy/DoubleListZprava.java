package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrDoubleList
 */
public enum DoubleListZprava {
    NULL_VSTUPNI_DATA("Vstupní data jsou null"),
    PRAZDNY_SEZNAM("Seznam je prázdný"),
    NENI_AKTUALNI_UKAZATEL("Ukazatel na aktuální prvek není nastaven"),
    NENI_DALSI_PRVEK("Není v seznamu další prvek: aktuální ukazatel je na posledním prvku"),
    NENI_PREDCHOZI_PRVEK("Není v seznamu předchozí prvek: aktuální ukazatel je na prvním prvku");

    private final String zprava;

    DoubleListZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
