package cz.upce.fei.bdats.perzistence;

import cz.upce.fei.bdats.halda.IAbstrHeap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Rozhraní deklaruje sadu základních operací ukládání/načtení datových entit do/z <i>CSV</i> souborů
 *
 * @param <E> Generický typ, jenž bude nahrazen určitým typem datových entit, s nimiž rozhraní pracuje
 */
public interface IPerzistence<E> {

    /**
     * Veřejné konstanty reprezentující cesty k:
     * <ul>
     * <li> Vzorovému <i>CSV</i> souboru
     * <li> Výchozímu <i>CSV</i> souboru
     * </ul>
     */
    String CESTA_VZOR = "src/main/java/cz/upce/fei/bdats/util/vzor.csv";
    String CESTA_KRAJE = "src/main/java/cz/upce/fei/bdats/util/kraje.csv";
    String CESTA_ULOZISTE = "src/main/java/cz/upce/fei/bdast/util/uloziste.csv";
    /**
     * Veřejná konstanta reprezentuje oddělovač <i>(eng. delimiter)</i> jednotlivých atributů podle struktuy
     * <i>CSV</i> souboru
     */
    String ODDELOVAC_ATRIBUTU = ";";
    /**
     * Veřejná konstanta reprezentuje indexy jednotlivých atributů podle struktury <i>CSV</i> souboru
     */
    int CELKEM_ATRIBUTU = 7;
    int INDEX_CISLA_KRAJE = 0;
    int INDEX_NAZVU_KRAJE = 1;
    int INDEX_PSC = 2;
    int INDEX_NAZVU_OBCE = 3;
    int INDEX_POCTU_MUZU = 4;
    int INDEX_POCTU_ZEN = 5;
    int INDEX_CELKEM = 6;

    /**
     * Načte data ze <i>CSV</i> souboru do prioritní fronty
     *
     * @param halda Prioritní fronta, do níž se načtou data ze souboru
     * @param cesta Cesta k <i>CSV</i> souboru
     *
     * @return {@code true}, pokud načtení proběhlo bez výjimek, jinak {@code false}
     *
     * @throws IOException Když dojde k <i>i/o</i> chybě při čtení ze souboru
     */
    boolean nactiCsv(IAbstrHeap<E> halda, String cesta) throws IOException;

    /**
     * Uloží data z prioritní fronty do <i>CSV</i> souboru
     *
     * @param halda Prioritní fronta, z níž se data uloží do souboru
     *
     * @return {@code true}, pokud uložení proběhlo bez výjimek, jinak {@code false}
     *
     * @throws IOException Když dojde k <i>i/o</i> chybě při zápisu do souboru
     */
    boolean ulozCsv(IAbstrHeap<E> halda) throws IOException;

    /**
     * Zkontroluje, zda soubor na dané cestě existuje a zároveň není prázdný
     *
     * @param cesta Cesta k <i>CSV</i> souboru
     *
     * @return {@code true}, pokud soubor existuje a není prázdný, jinak {@code false}
     */
    static boolean jeSoubor(String cesta) {
        final File soubor = new File(cesta);
        if (soubor.exists()) {
            try {
                final long velikost = Files.size(Paths.get(cesta));
                return velikost > 0;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
