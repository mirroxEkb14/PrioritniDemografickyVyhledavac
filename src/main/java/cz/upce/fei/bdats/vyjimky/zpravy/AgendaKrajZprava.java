package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.agenda.IAgendaKraj;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see IAgendaKraj
 */
public enum AgendaKrajZprava {
    SPATNA_POSLOUPNOST("Špatně zadaná posloupnost prvků"),
    PRAZDNA_HALDA("Prioritní fronta je prázdná"),
    PRAZDNY_TYP_PROHLIZENI("Typ prohlížení je prázdný");

    private final String zprava;

    AgendaKrajZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
