package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.model.Obec;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see Obec
 */
public enum ObecZprava {
    ZAPORNA_HODNOTA("Číslo nesmí být záporné"),
    PRAZDNY_RETEZEC("Textový řetězec nesmí být prázdný"),
    NEPLATNY_CELY_POCET("Celkový počet musí být roven součtu mužů a žen");

    private final String zprava;

    ObecZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
