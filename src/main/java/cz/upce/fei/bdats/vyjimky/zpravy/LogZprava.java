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
    CHYBA_SPATNA_POLE("Špatně nastavená pole: nesmí být prázdná a čísla musí být větší než nula"),
    CHYBA_TVURCE_PRAZDNY_KLIC("Název nesmí být prázdný"),
    CHYBA_GENERATORU_SPATNY_POCET("Špatně zvolá hodnota: počet musí být kladné celé číslo"),
    CHYBA_NACTENI_VZORU("Chyba při čtení dat ze vzorového .csv souboru: název obce musí být unikátní"),
    CHYBA_NACTENI_KRAJE("Chyba při čtení dat ze .csv souboru krajů: název obce musí být unikátní"),
    CHYBA_NACTENI_ULOZISTE("Chyba při čtení dat ze .csv souboru uložiště: název obce musí být unikátní"),
    INFO_ULOZENI("Data byla uložena do souboru"),
    CHYBA_ULOZENI("Chyba: data nebyla uložena"),
    INFO_NACTENI_VZORU("Vzorová halda byla úspěšně načtena"),
    INFO_NACTENI_KRAJE("Soubor krajů byl úspěšně načten"),
    INFO_NACTENI_ULOZISTE("Data byla úspěšně načtena z uložiště"),
    INFO_VLOZENI("Kraj byl úspěšně vložen");

    private final String zprava;

    LogZprava(String zprava) { this.zprava = zprava; }

    public String getZprava() { return zprava; }
}
