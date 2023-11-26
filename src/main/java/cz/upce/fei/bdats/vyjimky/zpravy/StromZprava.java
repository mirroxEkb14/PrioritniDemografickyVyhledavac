package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.strom.IAbstrTable;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAbstrTable
 */
public enum StromZprava {
    NEEXISTUJICI_KLIC("Vstupní klíč není platný"),
    NULL_KLIC("Hledaný klíč je null"),
    EXISTUJICI_KLIC("Vstulní klíč již existuje ve stromu"),
    PRAZDNY_ZASOBNIK("Chyba při provádění iterace: zásobník je prázdný a nelze odebrat prvek"),
    PRAZDNA_FRONTA("Chyba při provádění iterace: fronta je prázdná a nelze odebrat prvek"),
    PRAZDNY_KOREN("Kořen nebyl nalezen");

    private final String zprava;

    StromZprava(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
