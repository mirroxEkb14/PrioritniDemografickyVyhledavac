package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.sekvence.fifo.IAbstrFifo;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrFifo
 */
public enum FifoZprava {
    NULL_VSTUPNI_DATA("Vstupní data jsou null"),
    PRAZDNA_FRONTA("Fronta je prázdná");

    private final String zprava;

    FifoZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
