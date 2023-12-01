package cz.upce.fei.bdats.generator;

import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;

/**
 * Rozhraní deklaruje sadu základních operací pro generování dat a jejich následné vkládání do datové struktury
 *
 * @param <E> Generický typ reprezentující budoucí typ datových entit pro výrobu
 */
public interface Generator<E> {

    /**
     * Veřejné konstanty reprezentující maximální/minimální hodnoty pro číslo kraje při generování náhodných dat
     * prostřednictvím {@link java.util.Random#nextInt(int)}, což znamená <i>od {@code 1} (včetně) po {@code int}
     * (vyloučeno)</i> - proto je krajů {@code 15}, ačkoliv je jich skutečně {@code 14}
     */
    int CISLO_KRAJE_MAX = 15;
    int CISLO_KRAJE_MIN = 1;
    /**
     * Veřejná konstanta reprezentuje horní mez pro číslo používané v názvu obcí mající podobu "ObecX," kde x -
     * celé číslo v rozsahu 1 až 999
     */
    int CISLO_NAZVU_OBCE_MAX = 1000;
    /**
     * Veřejná konstanta reprezentuje horní mez pro počet mužů/žen (tj. maximální možnou hodnotu)
     */
    int POCET_LIDI_MAX = 1000;
    /**
     * Veřejná konstanta reprezentuje předpis pro název obce, od něhož se nádledně přidá příslušné číslo, což
     * znamená, že výstupní název má podobu "ObecX", kde x - celé číslo < konstanta {@link Generator#CISLO_NAZVU_OBCE_MAX}
     */
    String NAZEV_OBCE_PREDPIS = "Obec";
    /**
     * Veřejná konstanta reprezentuje předpis pro <i>Poštovní Směrovací Číslo</i>, kde každé x bude nahrazeno
     * číslem příslušného kraje
     */
    String PSC_PREDPIS = "XXX XX";
    /**
     * Veřejná konstanta reprezentuje symbol, jenž se použíná jako náhrada pro zatím neznámé hodnoty v PSČ - "X"
     */
    String NAHRADNY_BIT = "X";

    /**
     * Generuje náhodná data pro ten určitý typ objektů a vkládá je do datové struktury
     *
     * @param halda Prioritní fronta, pro nějž budou vygenerovány nové prvky
     * @param pocet Počet prvků, jež mají být vygenerovány a vloženy do binární haldy
     *
     * @throws CeleKladneCisloException Když je hodnota parametru {@code pocet} menší než {@code 0}
     */
    void generuj(IAbstrHeap<E> halda, int pocet) throws CeleKladneCisloException;
}
