package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.gui.koreny.ISeznamPanel;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see ISeznamPanel
 */
public enum SeznamPanelZprava {
    PRAZDNY_VSTUP_OBCE("Vstupní instance obce je null"),
    NEPLATNE_CELE_CISLO("Špatně zadané celé kladné číslo"),
    PRAZDNY_SEZNAM("Seznam obcí je prázdný"),
    PRAZDNY_VSTUP_TYPU("Vstupní typ prohlížení je null");

    private final String zprava;

    SeznamPanelZprava(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
