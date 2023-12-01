package cz.upce.fei.bdats.agenda;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.halda.AbstrHeap;
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.generator.Generator;
import cz.upce.fei.bdats.generator.ObecGenerator;
import cz.upce.fei.bdats.perzistence.IPerzistence;
import cz.upce.fei.bdats.perzistence.ObecPerzistence;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.vyjimky.zpravy.AgendaKrajZprava;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>ověření funkčnosti</i> implementováných ADS umožňující
 * <i>správu</i> obcí
 *
 * <p> Třída používá <b>Singleton</b> návrhový vzor, čímž zabezpečuje jedinou instanci v rámci aplikace
 *
 * <p> Implementuje rozhraní {@link IAgendaKraj}
 */
public final class AgendaKraj implements IAgendaKraj<Obec> {

    private static final Comparator<Obec> VYCHOZI_KOMPARATOR =
            Comparator.comparingInt(Obec::getCelkem);

    private IAbstrHeap<Obec> halda;
    private Generator<Obec> obecGenerator;
    private IPerzistence<Obec> perzistence;

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static AgendaKraj instance;

    public static AgendaKraj getInstance() {
        if (instance == null)
            instance = new AgendaKraj();
        return instance;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Privátní konstruktor">
    private AgendaKraj() { nastav(); }

    /**
     * <b>Poznámka</b>: Nezpracovává výjinku <b>catch (HeapException ignored) {}</b>, protože:
     * <ul>
     * <li> Kapacita haldy je vždy platná
     * <li> Komparátor nidky není {@code null}
     * </ul>
     */
    private void nastav() {
        try {
            this.halda = new AbstrHeap<>(Obec.class, VYCHOZI_KOMPARATOR);
            this.obecGenerator = new ObecGenerator();
            this.perzistence = new ObecPerzistence();
        } catch (HeapException ignored) {}
    }
// </editor-fold>

    @Override
    public void vybuduj(Obec[] pole) throws AgendaKrajException {
        try {
            halda.vybuduj(pole);
        } catch (HeapException ex) {
            throw new AgendaKrajException(
                    AgendaKrajZprava.SPATNA_POSLOUPNOST.zprava());
        }
    }

    /**
     * <b>Poznámka</b>: Nezpracovává výjimku <b>catch (HeapException ignored) {}</b>, protože:
     * <ul>
     * <li> Vstupní kritérium porovnávání je vždy platný
     * </ul>
     */
    @Override
    public void reorganizuj(Comparator<Obec> komp) throws AgendaKrajException {
        try {
            halda.reorganizuj(komp);
        } catch (HeapException ignored) {}
    }

    @Override
    public void zrus() { halda.zrus(); }

    @Override
    public boolean jePrazdna() { return halda.jePrazdna(); }

    @Override
    public int mohutnost() { return halda.mohutnost(); }

    @Override
    public Iterator<Obec> vytvorIterator(ETypProhl typ) { return halda.vytvorIterator(typ); }

    /**
     * <b>Poznámka</b>: Nezpracovává výjimku <b>catch (HeapException ignored) {}</b>, protože:
     * <ul>
     * <li> Vstupní obec nikdy není {@code null}
     * </ul>
     */
    @Override
    public void vloz(Obec obec) throws AgendaKrajException {
        try {
            halda.vloz(obec);
        } catch (HeapException ignored) {}
    }

    @Override
    public Obec odeberMax() throws AgendaKrajException {
        try {
            return halda.odeberMax();
        } catch (HeapException ex) {
            throw new AgendaKrajException(
                    AgendaKrajZprava.PRAZDNA_HALDA.zprava());
        }
    }

    @Override
    public Obec zpristupniMax() throws AgendaKrajException {
        try {
            return halda.zpristupniMax();
        } catch (HeapException ex) {
            throw new AgendaKrajException(
                    AgendaKrajZprava.PRAZDNA_HALDA.zprava());
        }
    }

    @Override
    public void generuj(int pocet) throws CeleKladneCisloException {
        try {
            obecGenerator.generuj(halda, pocet);
        } catch (CeleKladneCisloException ex) {
            throw new CeleKladneCisloException();
        }
    }

    @Override
    public @NotNull String vypis(ETypProhl typ) throws AgendaKrajException {
        try {
            return halda.vypis(typ);
        } catch (HeapException ex) {
            throw new AgendaKrajException(
                    AgendaKrajZprava.PRAZDNY_TYP_PROHLIZENI.zprava());
        }
    }

    @Override
    public boolean nacti(String cesta) {
        try {
            return perzistence.nactiCsv(halda, cesta);
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean uloz() {
        try {
            return perzistence.ulozCsv(halda);
        } catch (IOException ex) {
            return false;
        }
    }
}
