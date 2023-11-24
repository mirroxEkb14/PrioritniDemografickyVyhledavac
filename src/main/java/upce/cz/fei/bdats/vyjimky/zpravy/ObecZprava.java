package upce.cz.fei.bdats.vyjimky.zpravy;

/**
 * Výčtový typ slouží k definici konkrétních chybových zpráv, které mohou být použity v různých částech programu
 * při vyvolávání výjimek. Každá hodnota reprezentuje specifický typ chybové zprávy, který může být při chybách
 * předán do výjimky {@link upce.cz.fei.bdats.vyjimky.ObecException}
 *
 * <p> Použití {@link Enum} usnadňuje konzistentní a srozumitelnou identifikaci chyb v programu a umožňuje
 * centrální správu chybových zpráv na jednom místě
 */
public enum ObecZprava {
    CHYBA_ZAPORNA_HODNOTA("Číslo nesmí být záporné"),
    CHYBA_PRAZDNY_RETEZEC("Textový řetězec nesmí být prázdný"),
    CHYBA_NEPLATNY_CELY_POCET("Celkový počet musí být roven součtu mužů a žen");

    private final String zprava;

    ObecZprava(String zprava) { this.zprava = zprava; }

    public String zprava() { return this.zprava; }
}
