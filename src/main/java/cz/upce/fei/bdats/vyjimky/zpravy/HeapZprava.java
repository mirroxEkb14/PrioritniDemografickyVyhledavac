package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.halda.IAbstrHeap;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrHeap
 */
public enum HeapZprava {
    PRAZDNE_POLE("Vstupní pole je prázdné");

    private final String zprava;

    HeapZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
