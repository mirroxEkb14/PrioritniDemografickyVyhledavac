package cz.upce.fei.bdats.vyjimky.zpravy;

import cz.upce.fei.bdats.gui.komponenty.KomponentHalda;
import cz.upce.fei.bdats.gui.komponenty.KomponentPrikazy;

/**
 * Třída je výčtovým typem pro uchovávání chybových zpráv
 *
 * @see KomponentHalda
 * @see KomponentPrikazy
 */
public enum LogZprava {
    SPATNA_POLE("Špatně nastavená pole: nesmí být prázdná a čísla musí být větší než nula"),
    LOG_TVORENI_PRAZDNY_KLIC("Název nesmí být prázdný"),
    LOG_GENERATOR_SPATNY_POCET("Špatně zvolá hodnota: počet musí být kladné celé číslo"),
    LOG_NACTENI_VZORU("Chyba při čtení dat ze vzorového .csv souboru: název obce musí být unikátní"),
    LOG_NACTENI_KRAJE("Chyba při čtení dat ze .csv souboru krajů: název obce musí být unikátní"),
    LOG_NACTENI_ULOZISTE("Chyba při čtení dat ze .csv souboru uložiště: název obce musí být unikátní"),
    LOG_USPESNE_ULOZENI("Data byla uložena do souboru"),
    LOG_CHYBNE_ULOZENI("Chyba: data nebyla uložena");

    private final String zprava;

    LogZprava(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
