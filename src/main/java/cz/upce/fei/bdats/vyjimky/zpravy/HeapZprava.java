package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.halda.IAbstrHeap;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrHeap
 */
public enum HeapZprava {
    PRAZDNE_POLE("Vstupní pole je prázdné"),
    PRAZDNY_PRVEK("Vstupní prvek je null"),
    PRAZDNA_HALDA("Halda je prázdná"),
    NULL_INICIALIZACNI_KAPACITA("Inicializační kapacita musí být větší než 0"),
    NULL_INICIALIZACNI_KOMPARATOR("Porovnávací kritérium «komparátor» je null"),;

    private final String zprava;

    HeapZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
