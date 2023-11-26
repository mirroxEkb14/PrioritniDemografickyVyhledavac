package cz.upce.fei.bdats.strom;

import cz.upce.fei.bdats.sekvence.fifo.*;
import cz.upce.fei.bdats.sekvence.lifo.*;
import cz.upce.fei.bdats.vyjimky.FifoException;
import cz.upce.fei.bdats.vyjimky.StromException;
import cz.upce.fei.bdats.vyjimky.LifoException;
import cz.upce.fei.bdats.vyjimky.zpravy.StromZprava;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Třída implementuje sadu základních operací abstraktní datové strultury <i>(ADS)</i> <b>binární vyhledávací
 * strom</b> v dynamické paměti (tj. tabulka na binárním stromu)
 *
 * <p> Třída reprezentuje vyhledávací strukturu, kde je realizováno vyhledávání podle klíče
 *
 * <p> Iterátor využívá ADS zásobník {@link IAbstrLifo} a frontu {@link IAbstrFifo} postavené nad <b>lineárně
 * uspořádanou množinou</b> <i>(ADS</i> <b>obousměrně necyklický zřetězený lineární seznam</b> <i>- eng. Doubly
 * Linked List)</i> jako novou samostatnou třídou
 *
 * <p> Implementuje rozhraní {@link IAbstrTable}
 *
 * @param <K> Generický typ pro klíče, jenž implementuje {@link Comparable} pro uspořádání binárního stromu
 * @param <V> Generický typ pro hodnoty
 */
public final class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private Uzel koren;

    /**
     * Konstanty se používají v rámci třídy pro vyhnutí se magickým číslem
     */
    private static final int NULTA_HODNOTA = 0;
    private static final int JEDNICKA = 1;

    /**
     * Privátní třída reprezentuje jednotlivý uzel stromu
     */
    private class Uzel {
        K klic;
        V hodnota;
        Uzel rodic;
        Uzel vlevo;
        Uzel vpravo;

        Uzel(K klic, V hodnota, Uzel rodic) {
            this.klic = klic;
            this.hodnota = hodnota;
            this.rodic = rodic;
            vlevo = vpravo = null;
        }
    }

    /**
     * Konstruktor
     * <p>
     * Inicializuje kořen stromu jako {@code null}
     */
    public AbstrTable() { koren = null; }

// <editor-fold defaultstate="collapsed" desc="Metoda: void zrus()">
    @Override
    public void zrus() { koren = null; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: boolean jePrazdny()">
    @Override
    public boolean jePrazdny() { return koren == null; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: int mohutnost()">
    @Override
    public int mohutnost() { return mohutnostRekurzivne(koren); }

    /**
     * Popis logiky:
     * <ol>
     * <li> Pokud je uzel {@code null}, vrátí {@code 0}, protoře dosáhla konce větve
     *      <ul>
     *      <li> <b>if(uzel == null)</b>
     *      </ul>
     * <li> <b>else</b>
     *      <ul>
     *      <li> <b>pocetVlevo = mohutnostRekurzivne(uzel.vlevo)</b>: Spočítá počet uzlů vlevo
     *      <li> <b>pocetVpravo = mohutnostRekurzivne(uzel.vpravo)</b>: Spočítá počet uzlů vpravo
     *      <li> <b>return</b>: Spočítá a vrátí počet uzlů pod tímto uzlem:
     *          <ol>
     *          <li> Sečte počet uzlů vlevo a vpravo
     *          <li> Přidá {@code 1} pro aktuální uzel
     *          </ol>
     *      </ul>
     * </ol>
     *
     * @param uzel Aktuální prvek
     *
     * @return Celkový počet uzlů pod kořenem
     */
    private int mohutnostRekurzivne(Uzel uzel) {
        if (uzel == null) {
            return NULTA_HODNOTA;
        } else {
            int pocetVlevo = mohutnostRekurzivne(uzel.vlevo);
            int pocetVpravo = mohutnostRekurzivne(uzel.vpravo);
            return JEDNICKA + pocetVlevo + pocetVpravo;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: Iterator<V> vytvorIterator(ETypProhl typ)">
    @Override
    public Iterator<V> vytvorIterator(ETypProhl typ) {
        return switch (typ) {
            case SIRKA -> new SirkaIterator();
            case HLOUBKA -> new HloubkaIterator();
        };
    }

    /**
     * <b>Breadth-First Search (BFS)</b> je strategie pro prohledávání stromu postupující "široce" v rámci
     * jednoho levelu před tím, než se pohne na další level
     */
    private class SirkaIterator implements Iterator<V> {

        private final IAbstrFifo<Uzel> fronta;

        public SirkaIterator() {
            fronta = new AbstrFifo<>();
            try {
                if (koren != null)
                    fronta.vlozNaKonec(koren);
            } catch (FifoException ignored) {}
        }

        @Override
        public boolean hasNext() { return !fronta.jePrazdna(); }

        /**
         * Aktuální prvek (uzel) se nastaví na prvek odebraný z fronty, čímž získá následující prvek k procházení.
         * Následně, pokud má aktuální uzel levého/pravého potomka, přidá ho do fronty pro další průchod
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Když není další prvek k dispozici
         */
        @Override
        public V next() {
            if (!hasNext())
                throw new NoSuchElementException();
            try {
                final Uzel aktualniUzel = fronta.odeberZeZacatku();
                if (aktualniUzel.vlevo != null)
                    fronta.vlozNaKonec(aktualniUzel.vlevo);
                if (aktualniUzel.vpravo != null)
                    fronta.vlozNaKonec(aktualniUzel.vpravo);
                return aktualniUzel.hodnota;
            } catch (FifoException ex) {
                throw new NoSuchElementException(
                        StromZprava.PRAZDNA_FRONTA.getZprava());
            }
        }
    }

    /**
     * <b>Depth-First Search (DFS)</b> je strategie pro procházení stromu do hloubky předtím, než se vrací
     * zpět k dalším větvím
     *
     * <p> <b>In-order DFS</b> nejprve navštíví levý podstrom, poté kořenový uzel a nakonec pravý podstrom
     */
    private class HloubkaIterator implements Iterator<V> {

        private final IAbstrLifo<Uzel> zasobnik;
        private Uzel aktualniUzel;

        public HloubkaIterator() {
            zasobnik = new AbstrLifo<>();
            aktualniUzel = koren;
        }

        @Override
        public boolean hasNext() { return !zasobnik.jePrazdny() || aktualniUzel != null; }

        /**
         * Nejprve přidává všechny uzly (prvky) levého podstromu do zásobníku, poté postupně odebírá uzly z
         * vrcholu zásobníku a přidává uzly pravého podstromu, pokud existují, čímž zajišťuje návštěvu uzlů
         * v pořadí od nejmenšího po největší klíč
         *
         * <p> Popis logiký jednotlivých bloků kódu:
         * <ol>
         * <li> <b>while (aktUzel != null)</b>: Vytváří cestu směrem k nejlevějšímu (nejmenšímu) uzlu stromu
         * <li> Odebere uzel z vrcholu zásobníku a přejde na pravého potomka tohoto uzlu, pak pokračuje v iteraci,
         * čímž postupně prochází strom zleva doprava
         *     <ul>
         *     <li> <b>aktUzel = zasobnik.odeber()</b>
         *     <li> <b>aktUzel = aktUzel.vpravo</b>
         *     </ul>
         * </ol>
         *
         * @return Další prvek pro zpracování
         *
         * @throws NoSuchElementException Když není další prvek k dispozici
         */
        @Override
        public V next() {
            try {
                while (aktualniUzel != null) {
                    zasobnik.vlozNaZacatek(aktualniUzel);
                    aktualniUzel = aktualniUzel.vlevo;
                }
                aktualniUzel = zasobnik.odeberZeZacatku();
                final V hodnota = aktualniUzel.hodnota;
                aktualniUzel = aktualniUzel.vpravo;
                return hodnota;
            } catch (LifoException e) {
                throw new NoSuchElementException(
                        StromZprava.PRAZDNY_ZASOBNIK.getZprava());
            }
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: V najdi(K klic)">
    /**
     * Popis logiky:
     * <ol>
     * <li> Zajišťuje, že vstupní klíč a strom (koren) nejsou prázdné
     *     <ul>
     *     <li> <b>pozadatNePrazdnyKlic(klic)</b>
     *     <li> <b>pozadatNePrazdnyKoren()</b>
     *     </ul>
     * <li> Rekurzivně vyhledává hodnoty v stromu až nenajde uzel, který obsahuje hledanou hodnotu, následně
     * vrátí hodnotu tohoto uzlu. Pokud ale je nalezený uzel {@code null}, vyvolá výjimku
     *     <ul>
     *     <li> <b>final Uzel uzel = najdiRekurzivne(koren, klic)</b>
     *     <li> <b>if (uzel == null)</b>
     *     <li> <b>return uzel.hodnota</b>
     *     </ul>
     * </ol>
     *
     * @see AbstrTable#najdiRekurzivne(Uzel, Comparable)
     */
    @Override
    public V najdi(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        final Uzel uzel = najdiRekurzivne(koren, klic);
        if (uzel == null)
            throw new StromException(
                    StromZprava.NEEXISTUJICI_KLIC.getZprava());
        return uzel.hodnota;
    }

    /**
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Pokud je {@code uzel} prázdný (neexistuje), vrátí {@code null}
     *     <ul>
     *     <li> <b>if (uzel == null)</b>
     *     </ul>
     * <li> Porovnává klíč, podle kterého se provádí vyhledívaní s klíčem aktuálního uzlu
     *     <ul>
     *     <li> <b>jeNula(klic, uzel.klic)</b>: Pokud klíče jsou ekvivalentní, vrátí nalezený {@code uzel}
     *     <li> <b>jeZaporne(klic, uzel.klic)</b>: Pokud klíč  je menší než klíč uzlu, pokračuje hledat v levém
     *     podstromu rekurzivně
     *     <li> <b>jeKladne(klic, uzel.klic)</b>: Pokud klíč je větší než klíč uzlu, pokračuje hledat v pravém
     *     podstromu rekurzivně
     *     </ul>
     * </ol>
     *
     * @param uzel Aktuální uzel, v němž se provádí vyhledávání
     * @param klic Klíč, podle něhož se vyhledává hodnota
     *
     * @return {@link Uzel} odpovídající zadanému klíči pokud hodnota byla nalezena, jinak {@code null}
     */
    private Uzel najdiRekurzivne(Uzel uzel, K klic) {
        if (uzel == null)
            return null;

        if (jeNula(klic, uzel.klic))
            return uzel;
        if (jeZaporne(klic, uzel.klic))
            return najdiRekurzivne(uzel.vlevo, klic);
        if (jeKladne(klic, uzel.klic))
            return najdiRekurzivne(uzel.vpravo, klic);

        return null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: void vloz(K klic, V hodnota)">
    /**
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Zkontroluje, zda klíč není prázdný a zda klíč již existuje ve stromu
     *     <ul>
     *     <li> <b>pozadatNePrazdnyKlic(klic)</b>
     *     <li> <b>if (jeExistujicimKlicem(klic))</b>
     *     </ul>
     * <li> Pokud kořen stromu existuje, provede rekurzivní vkládání nového uzlu, v opačmém případě (kořen
     * neexistuje), vytvoří nový uzel
     *     <ul>
     *     <li> <b>if (koren == null)</b>
     *     <li> <b>else</b>
     *     </ul>
     * </ol>
     *
     * @see AbstrTable#vlozRekurzivne(Comparable, Object, Uzel)
     */
    @Override
    public void vloz(K klic, V hodnota) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNeexistujiciKlic(klic);

        if (koren == null)
            koren = new Uzel(klic, hodnota, null);
        else
            vlozRekurzivne(klic, hodnota, koren);
    }

    /**
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Porovná klíče a rozhodne, do kterého podstromu nový uzel patří
     *     <ul>
     *     <li> <b>jeKladne(aktualniUzel.klic, klic)</b>: Pokud klíč uzlu je větší než klíč nového uzlu {@code klic}:
     *          <ul>
     *          <li> <b>if(aktualniUzel.vlevo == null)</b>: Vloží nový uzel do levého podstromu
     *          <li> <b>else</b>: Pokud levý potomek uzlu {@code aktualniUzel.vlevo} není prázdný, volá se rekurzivně
     *          tato metoda pro tohoto levého potomka
     *          </ul>
     *     <li> <b>else</b>: Pokud klíč uzlu je menší nebo roven klíči nového uzlu {@code klic}:
     *          <ul>
     *          <li> <b>if(aktualniUzel.vpravo == null)</b>: Vloží nový uzel do pravého podstromu
     *          <li> <b>else</b>: Pokud pravý potomek uzlu {@code aktualniUzel.vpravo} není prázdný, volá se
     *          rekurzivně tato metoda pro pravého potomka
     *          </ul>
     *     </ul>
     * </ol>
     *
     * @param klic Klíč, jenž se má vložit
     * @param hodnota Hodnota, jež se má vložit
     * @param aktualniUzel Aktuální uzel, v němž se hledá místo pro vložení nového uzlu (prvku)
     */
    private void vlozRekurzivne(K klic, V hodnota, @NotNull Uzel aktualniUzel) {
        if (jeKladne(aktualniUzel.klic, klic)) {
            if (aktualniUzel.vlevo == null)
                aktualniUzel.vlevo = new Uzel(klic, hodnota, aktualniUzel);
            else
                vlozRekurzivne(klic, hodnota, aktualniUzel.vlevo);
        } else {
            if (aktualniUzel.vpravo == null)
                aktualniUzel.vpravo = new Uzel(klic, hodnota, aktualniUzel);
            else
                vlozRekurzivne(klic, hodnota, aktualniUzel.vpravo);
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: V odeber(K klic)">
    /**
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> Provádí počáteční ověření vstupního klíče {@code klic} a existence kořene stromu
     *      <ul>
     *      <li> <b>pozadatNePrazdnyKlic()</b>
     *      <li> <b>pozadatNePrazdnyKoren()</b>
     *      </ul>
     * <li> Hledá {@code uzel} s odpovídajícím klíčem {@code klic} a zkontroluje ho na {@code null}
     *      <ul>
     *      <li> <b>najdiUzel()</b>
     *      <li> <b>if (uzel == null)</b>
     *      </ul>
     * <li> Připraví proměnnou {@code odebranaHodnota} pro následný návrat, pak ověří existence obou
     * potomků u uzlu {@code uzel}
     *      <ul>
     *      <li> <b>final V odebranaHodnota = uzel.hodnota</b>
     *      <li> <b>if (jsouObaPotomky(uzel))</b>: Zjišťuje následníka uzlu, tj. uzel s nejnižším
     *      klíčem v pravém podstromu, pak klíč a hodnota následníka jsou přesunuty na aktuální uzel
     *      {@code uzel}, čímž odebere aktuální uzel a přesune se k následníkovi
     *      </ul>
     * <li> Zpracovává případy, kdy uzel má buď levého nebo pravého potomka, nebo je listem
     *      <ul>
     *      <li> <b>if (jeLevyPotomek(uzel))</b>: {@code uzel} (minule následník uzlu) má levého potomka,
     *      a proto tento {@code uzel} bude nahrazen jeho levým potomkem
     *      <li> <b>else if (jePravyPotomek(uzel))</b>: {@code uzel} má pravého potomka, a proto provede
     *      obdobnou operaci s pravým potomkem
     *      <li> <b>else if (jeListem(uzel))</b> {@code uzel} je listem, a proto odstraní ten samý {@code uzel}
     *      </ul>
     * </ol>
     *
     * @see AbstrTable#najdiNaslednika(Uzel)
     * @see AbstrTable#odeberUzelSJednimPotomkem(Uzel, Uzel)
     * @see AbstrTable#odeberUzelBezPotomku(Uzel)
     */
    @Override
    public V odeber(K klic) throws StromException {
        pozadatNePrazdnyKlic(klic);
        pozadatNePrazdnyKoren();

        Uzel uzel = najdiRekurzivne(koren, klic);
        if (uzel == null)
            throw new StromException(
                    StromZprava.NEEXISTUJICI_KLIC.getZprava());

        final V odebranaHodnota = uzel.hodnota;
        if (jsouObaPotomky(uzel)) {
            final Uzel naslednik = najdiNaslednika(uzel);
            uzel.klic = naslednik.klic;
            uzel.hodnota = naslednik.hodnota;
            uzel = naslednik;
        }

        if (jeLevyPotomek(uzel)) {
            odeberUzelSJednimPotomkem(uzel, uzel.vlevo);
        } else if (jePravyPotomek(uzel)) {
            odeberUzelSJednimPotomkem(uzel, uzel.vpravo);
        } else if (jeListem(uzel)) {
            odeberUzelBezPotomku(uzel);
        }
        return odebranaHodnota;
    }

    /**
     * Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> <b>maPravehoPotomka(uzel)</b>: Pokud uzel má pravého potomka, najde nejlevější uzel v rámci tohoto
     * pravého podstromu (tj. najde první uzel v {@code in-order} následování)
     * <li> <b>while()</b>: Pokud uzel nemá pravého potomka, hledá prvního předka, jehož levý potomek je tento uzel.
     * To znamená, že se metoda vrátí k rodiči uzlu a postupně posune vzhůru po stromě a hledá rodiče, kteří ještě
     * nemají tento uzel jako pravého potomka. Jakmile najde takového rodiče nebo dojde k samotnému kořeni stromu,
     * vrátí tento uzel jako následníka (např. samotný kořen)
     * </ol>
     */
    private Uzel najdiNaslednika(Uzel uzel) {
        if (jePravyPotomek(uzel)) {
            uzel = uzel.vpravo;
            while (uzel.vlevo != null)
                uzel = uzel.vlevo;
            return uzel;
        }
        Uzel rodic = uzel.rodic;
        while (rodic != null && uzel == rodic.vpravo) {
            uzel = rodic;
            rodic = uzel.rodic;
        }
        return rodic;
    }

    /**
     * Odstraní uzel, jenž má právě jednoho potomka, a následné připojí tohoto potomka na místo odebraného uzlu
     *
     * <p> Popis logiký jednotlivých bloků kódu:
     * <ol>
     * <li> <b>jeKorenem()</b>: Aktualizuje kořen, čímž se potomek stane novým kořenem stromu
     * <li> <b>jeLevymPotomkem()</b>: Připojí potomka na místo odebraného uzlu (tj. na levý potomek svého rodiče),
     * což znamená, že potomek se stane novým levým potomkem rodiče odebraného uzlu
     * <li> <b>jePravymPotomkem()</b>: Potomek se připojí na místo odebraného uzlu (tj. potomek se stane novým
     * pravým potomkem rodiče odebraného uzlu)
     * <li> <b>potomek.rodic = uzel.rodic</b>: Aktualizuje rodiče potomka tak, aby ukazoval na rodiče odebraného
     * uzlu
     * </ol>
     *
     * @param uzel Prvek, jenž má být odebrán
     * @param potomek Prvek, jenž bude připojen na místo odebraného uzlu
     */
    private void odeberUzelSJednimPotomkem(Uzel uzel, Uzel potomek) {
        if (jeKorenem(uzel))
            koren = potomek;
        else if (jeLevymPotomkem(uzel))
            uzel.rodic.vlevo = potomek;
        else if (jePravymPotomkem(uzel))
            uzel.rodic.vpravo = potomek;
        potomek.rodic = uzel.rodic;
    }

    /**
     * Popis logiky:
     * <ol>
     * <li> <b>jeKorenem()</b>: Nastaví kořen stromu na {@code null}, protože odebraný uzel je kořenem stromu
     * (celý strom je tedy prázdný)
     * <li> <b>jeLevymPotomkem()</b>: Nastaví levý potomek rodiče na {@code null}, protože odebraný uzel je
     * levým potomkem svého rodiče
     * <li> <b>jePravymPotomkem()</b>: Nastaví pravý potomek rodiče na {@code null}, protože odebraný uzel je
     * pravým potomkem svého rodiče
     * </ol>
     *
     * @param uzel Prvek, jenž má být odebrán
     */
    private void odeberUzelBezPotomku(Uzel uzel) {
        if (jeKorenem(uzel))
            koren = null;
        else if (jeLevymPotomkem(uzel))
            uzel.rodic.vlevo = null;
        else if (jePravymPotomkem(uzel))
            uzel.rodic.vpravo = null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metoda: String vypis(ETypProhl typ)">
    private static final String ODRADKOVANI = "\n";

    /**
     * Příklad výpisu:
     * <br> Obec{..., nazevObce='e', ...}
     * <br> Obec{..., nazevObce='c', ...}
     * <br> Obec{..., nazevObce='f', ...}
     * <br> Obec{..., nazevObce='a', ...}
     * <br> Obec{..., nazevObce='d', ...}
     * <br> Obec{..., nazevObce='h', ...}
     * <br> Obec{..., nazevObce='b', ...}
     * <br> Obec{..., nazevObce='g', ...}
     * <br> Obec{..., nazevObce='i', ...}
     *
     * @see IAbstrTable#vypis(ETypProhl)
     */
    @Override
    public @NotNull String vypis(ETypProhl typ) {
        StringBuilder sb = new StringBuilder();
        Iterator<V> iterator = switch (typ) {
            case SIRKA -> new SirkaIterator();
            case HLOUBKA -> new HloubkaIterator();
        };

        while (iterator.hasNext()) {
            switch (typ) {
                case SIRKA -> {
                    final V hodnota = iterator.next();
                    sb.append(hodnota).append(ODRADKOVANI);
                }
                case HLOUBKA -> sb.append(iterator.next()).append(ODRADKOVANI);
            }
        }

        return sb.toString();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní metody: Porovnávání klíčů">
    /**
     * Porovná výsledek metody compareTo s nulou
     *
     * <p>{@link Comparable#compareTo(Object)} vratí nulu pokud aktuální instance {@code this} je "rovná" druhé instanci {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     *
     * @return {@code true}, pokud výsledek je roven nule, jinak {@code false}
     */
    private boolean jeNula(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) == NULTA_HODNOTA; }

    /**
     * Porovná výsledek metody compareTo s kladným číslem
     *
     * <p>{@link Comparable#compareTo(Object)} vratí kladné číslo pokud aktuální instance {@code this} je "větší" než druhá instance {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     *
     * @return {@code true}, pokud výsledek je kladný, jinak {@code false}
     */
    private boolean jeKladne(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) > NULTA_HODNOTA; }

    /**
     * Porovná výsledek metody compareTo s záporným číslem
     *
     * <p>{@link Comparable#compareTo(Object)} vratí záporné číslo pokud aktuální instance {@code this} je "menší" než druhá instance {@code other}
     *
     * @param obj1 První objekt pro porovnání
     * @param obj2 Druhý objekt pro porovnání
     * @return {@code true}, pokud výsledek je záporný, jinak {@code false}
     */
    private boolean jeZaporne(@NotNull K obj1, K obj2) { return obj1.compareTo(obj2) < NULTA_HODNOTA; }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní metody: Zjišťováví vztahů">
    private boolean jsouObaPotomky(@NotNull Uzel uzel) {
    return uzel.vlevo != null && uzel.vpravo != null;
}

    private boolean jePravyPotomek(@NotNull Uzel uzel) {
        return uzel.vpravo != null;
    }

    private boolean jeLevyPotomek(@NotNull Uzel uzel) {
        return uzel.vlevo != null;
    }

    private boolean jeKorenem(@NotNull Uzel uzel) {
        return uzel.rodic == null;
    }

    private boolean jeListem(@NotNull Uzel uzel) {
        return uzel.vlevo == null && uzel.vpravo == null;
    }

    private boolean jeLevymPotomkem(@NotNull Uzel uzel) {
        return uzel == uzel.rodic.vlevo;
    }

    private boolean jePravymPotomkem(@NotNull Uzel uzel) {
        return uzel == uzel.rodic.vpravo;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Metody ošetřování typu pozadat()">
    /**
     * Ověřuje, zda kořen stromu není prázdný {@code null}
     *
     * @throws StromException Pokud je kořen prázdný
     */
    private void pozadatNePrazdnyKoren() throws StromException {
        if (koren == null)
            throw new StromException(
                    StromZprava.PRAZDNY_KOREN.getZprava());
    }

    /**
     * Ověřuje, zda zadaný klíč není prázdný {@code null}
     *
     * @param klic Vstupní klíč ověřující se na prázdnost
     *
     * @throws StromException Pokud je klíč prázdný
     */
    private void pozadatNePrazdnyKlic(K klic) throws StromException {
        if (klic == null)
            throw new StromException(
                    StromZprava.NULL_KLIC.getZprava());
    }

    /**
     * Provádí rekurzivní prohledávání stromu, dokud nenalezne uzel se zadaným klíčem
     *
     * @param klic Klíč, jenž se má zkontrolovat na existenci ve stromu
     *
     * @throws StromException Když klíč již existuje ve stromu
     */
    private void pozadatNeexistujiciKlic(K klic) throws StromException {
        if (najdiRekurzivne(koren, klic) != null)
            throw new StromException(
                    StromZprava.EXISTUJICI_KLIC.getZprava());
    }
// </editor-fold>
}
