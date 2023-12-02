package cz.upce.fei.bdats.gui;

import cz.upce.fei.bdats.gui.koreny.PrikazPanel;

/**
 * {@link Enum} obsahující titulky pro tlačítka v rámci {@link PrikazPanel}u
 */
public enum Titulek {
    KOMPONENT_HALDA("Prioritní fronta"),
    BTN_VYBUDUJ("Vybuduj"),
    BTN_VLOZ("Vlož"),
    BTN_ODEBER_MAX("OdeberMax"),
    BTN_ZPRISTUPNI_MAX("ZpřístupniMax"),
    CB_VYPIS("Vypiš"),
    CB_SIRKA("Šířka"),
    CB_HLOUBKA("Hloubka"),
    CB_VRAT("Vrať"),
    BTN_PRAZDNOST("Prázdnost"),
    BTN_ZRUS("Zruš"),
    TLACITKO_FAJN("Fajn"),
    TLACITKO_ZRUSIT("Zrušit"),
    LABEL_CISLO_KRAJE("Číslo kraje: "),
    LABEL_NAZEV_KRAJE("Název kraje: "),
    LABEL_NAZEV_OBCE("Název obce: "),
    LABEL_PSC("PSČ obce: "),
    LABEL_MUZE("Počet mužů: "),
    LABEL_ZENY("Počet žen: "),
    HLAVICKA_DIALOG_VLOZENI("Vytvoření Nové Obce"),
    HLAVICKA_TITULKOVEHO_PANELU_VLOZENI("Zadávání údajů"),
    KOMPONENT_PRIKAZY("Příkazy"),
    BTN_GENERUJ("Generuj"),
    CB_NACTI("Načti"),
    CB_VZOR("vzor.csv"),
    CB_KRAJE("kraje.csv"),
    CB_ULOZISTE("uložiště.csv"),
    LABEL_POCET_PRVKU("Počet: "),
    HLAVICKA_DIALOGU_GENERATOR("Generátor Obcí"),
    HLAVICKA_TITULKOVEHO_PANELU_GENERATORU("Zadávání počtu obcí"),
    BTN_ULOZ("Ulož"),
    CB_REORGANIZUJ("Reorganizuj"),
    CB_POCET_OBYVATEL("Počet obyvetel"),
    CB_NAZEV_OBCE("Název obce");

    private final String nadpis;

    Titulek(String nadpis) { this.nadpis = nadpis; }

    public String nadpis() { return nadpis; }
}
