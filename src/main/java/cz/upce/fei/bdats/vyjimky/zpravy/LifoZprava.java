package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.sekvence.lifo.IAbstrLifo;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrLifo
 */
public enum LifoZprava {
    NULL_VSTUPNI_DATA("Vstupní data jsou null"),
    PRAZDNY_ZASOBNIK("Zásobník je prázdný");

    private final String zprava;

    LifoZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
