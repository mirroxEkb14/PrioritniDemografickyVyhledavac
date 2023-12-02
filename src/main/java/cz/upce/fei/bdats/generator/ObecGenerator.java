package cz.upce.fei.bdats.generator;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.vyjimky.ObecException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Random;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro generování dat a jejich následné vkládání do <b>prioritní
 * fronty</b>
 *
 * <p> Implementuje rozhraní {@link Generator}
 */
public final class ObecGenerator implements Generator<Obec> {

    private static final Random random = new Random();
    private static final String[] KRAJE = {"Hlavni mesto Praha", "Jihocesky", "Jihomoravsky", "Karlovarsky",
            "Kraj Vysocina", "Kralovehradecky", "Liberecky", "Moravskoslezsky", "Olomoucky", "Pardubicky",
            "Plzensky", "Stredocesky", "Ustecky", "Zlinsky"};

// <editor-fold defaultstate="collapsed" desc="Metoda: void generuj(IAbstrHeap<Obec> halda, int pocet)">
    /**
     * <b>Poznámka</b>: Nezpracovává výjimky <b>catch (ObecException | HeapException ignored) {}</b>, protože:
     * <ul>
     * <li> Do konstruktoru obce jsou vždy předány pouze platné hodnoty
     * <li> Hodnota atributu metody vložení prvku do halda je vždy platná
     * </ul>
     */
    @Override
    public void generuj(@NotNull IAbstrHeap<Obec> halda, int pocet) {
        try {
            do {
                final int cisloKraje = generujNahodneCisloKraje();
                final String nazevKraje = generujNazevKraje(cisloKraje);
                final String nazevObce = generujUnikatniNazevObce(halda);
                final String psc = generujNahodnePsc(cisloKraje);
                final int pocetMuzu = generujNahodnyPocet();
                final int pocetZen = generujNahodnyPocet();
                final int celkem = pocetMuzu + pocetZen;

                final Obec obec = new Obec(cisloKraje,
                        nazevKraje,
                        nazevObce,
                        psc,
                        pocetMuzu,
                        pocetZen,
                        celkem);
                halda.vloz(obec);
            } while (--pocet != 0);
        } catch (ObecException | HeapException ignored) {}
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: Obec generujPodlePoctu(int celkem)">
    @Override
    public @NotNull Obec generujPodlePoctu(int celkem) {
        final int cisloKraje = generujNahodneCisloKraje();
        return new Obec(cisloKraje,
                generujNazevKraje(cisloKraje),
                generujNahodnyNazevObce(),
                generujNahodnePsc(cisloKraje),
                dejPodilMuzu(celkem),
                dejPodilZen(celkem),
                celkem);
    }

    /**
     * Vrátí 40% hodnoty proměnné {@code celkem}, což reprezentuje podíl mužů, přičemž se zaokrouhlí dolů
     *
     * @param celkem Celkový počet obyvatel v obci
     *
     * @return 40% hodnoty atributu
     */
    private int dejPodilMuzu(int celkem) {
        return (int) Math.floor((celkem * 0.4));
    }

    /**
     * Vrátí 60% hodnoty proměnné {@code celkem}, což reprezentuje podíl žen, přičemž se zaokrouhlí nahoru
     *
     * @param celkem Celkový počet obyvatel v obci
     *
     * @return 60% hodnoty atributu
     */
    private int dejPodilZen(int celkem) {
        return (int) Math.ceil((celkem * 0.6));
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: Obec generujPodleNazvu(String nazevObce)">
    @Override
    public @NotNull Obec generujPodleNazvu(String nazevObce) {
        final int cisloKraje = generujNahodneCisloKraje();
        final int pocetMuzu = generujNahodnyPocet();
        final int pocetZen = generujNahodnyPocet();

        return new Obec(cisloKraje,
                generujNazevKraje(cisloKraje),
                nazevObce,
                generujNahodnePsc(cisloKraje),
                pocetMuzu,
                pocetZen,
                pocetMuzu + pocetZen);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní Metody">
    /**
     * Generuje náhodné číslo kraje od {@code 1} (včetně) do {@code 15} (vyloučeno)
     *
     * @return Náhodně celé čísla v rozsahu od {@link Generator#CISLO_KRAJE_MIN} do {@link Generator#CISLO_KRAJE_MAX}
     */
    private int generujNahodneCisloKraje() {
        return random.nextInt(Generator.CISLO_KRAJE_MIN,
                              Generator.CISLO_KRAJE_MAX);
    }

    /**
     * Vytáhne název kraje z konstanty podle jeho čísla <i>(pre-increment se nutný kvůli indexování)</i>
     *
     * @param cisloKraje Číslo kraje vygenerované metodou {@link ObecGenerator#generujNahodneCisloKraje()}
     *
     * @return Textový řetězec s názvem kraje
     */
    private @NotNull String generujNazevKraje(int cisloKraje) { return KRAJE[--cisloKraje]; }

    /**
     * Generuje unikátní název obce tím, že projde celou prioritní fronty iterátorem a zkontroluje, zda
     * nějaký uzel již má stejný název obec
     *
     * @param halda Instace prioritní fronty
     *
     * @return Textový řetězec s unikátním názvem obce podle předpisu
     */
    private @NotNull String generujUnikatniNazevObce(@NotNull IAbstrHeap<Obec> halda) {
        String nazevObce;
        do {
            nazevObce = generujNahodnyNazevObce();
        } while (obsahujeVHalde(halda, nazevObce));
        return nazevObce;
    }

    /**
     * Projde celou binární haldu a zkontroluje, zda nějaký uzel má stejný název obce než ten, jenž byl náhodně
     * vygenerován pomocí {@link ObecGenerator#generujNahodnyNazevObce()}
     *
     * @param halda Instance prioritní fronty
     * @param nazev Vygenerovaný název obce pro kontrolu
     *
     * @return {@code true}, pokud daný název obce se vyskytuje v haldě, jinak {@code false}
     *
     * @see ObecGenerator#generujNahodnyNazevObce()
     */
    private boolean obsahujeVHalde(@NotNull IAbstrHeap<Obec> halda, String nazev) {
        final Iterator<Obec> iterator = halda.vytvorIterator(ETypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            final Obec obec = iterator.next();
            if (obec.getNazevObce().equals(nazev)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vytvoří název podle předpisu "ObecX", kde x - náhodné číslo v rozsahu od {@code 1} do
     * {@link Generator#CISLO_NAZVU_OBCE_MAX}
     *
     * @return Textový řetězec s názvem obce podle předpisu <i>(např. "Obec1", "Obec2" apod.)</i>
     */
    private @NotNull String generujNahodnyNazevObce() {
        return Generator.NAZEV_OBCE_PREDPIS + random.nextInt(Generator.CISLO_NAZVU_OBCE_MAX);
    }

    /**
     * Generuje PSC podle předpisu, kde jednotlivý znak 'x' bude vyměněn císlem kraje
     *
     * @param cisloKraje Číslo kraje vygenerované pomocí {@link ObecGenerator#generujNahodneCisloKraje()}
     *
     * @return Textový řetězec s PSC
     */
    private @NotNull String generujNahodnePsc(int cisloKraje) {
        return Generator.PSC_PREDPIS.replaceAll(
                Generator.NAHRADNY_BIT,
                String.valueOf(cisloKraje));
    }

    /**
     * Generuje náhodný počet buď mužů, anebo žen v rozsahu od {@code 1} do {@link Generator#POCET_LIDI_MAX}
     *
     * @return Náhodné číslo reprezentující počet mužů/žen
     */
    private int generujNahodnyPocet() { return random.nextInt(Generator.POCET_LIDI_MAX); }
// </editor-fold>
}
