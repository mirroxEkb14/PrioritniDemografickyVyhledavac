package cz.upce.fei.bdats.agenda;

import cz.upce.fei.bdats.halda.AbstrHeap;
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.generator.Generator;
import cz.upce.fei.bdats.generator.ObecGenerator;
import cz.upce.fei.bdats.perzistence.IPerzistence;
import cz.upce.fei.bdats.perzistence.ObecPerzistence;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.vyjimky.zpravy.ChybovaZpravaKraje;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Tato třída slouží k manipulaci s daty o obcích kraje. Slouží také jako rozhraní mezi uživatelem a datovou
 * strukturou, která uchovává informace o obcích v kraji. Implementuje základní operace pro manipulaci s
 * obcemi
 *
 * <p>Třída používá Singleton návrhový vzor, čímž zabezpečuje jedinou instanci v rámci aplikace
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
            throw new AgendaKrajException("");
        }
    }

    @Override
    public void reorganizuj(Comparator<Obec> komp) throws AgendaKrajException {
        try {
            halda.reorganizuj(komp);
        } catch (HeapException ex) {
            throw new AgendaKrajException("");
        }
    }

    @Override
    public void vloz(@NotNull Obec obec) throws AgendaKrajException {
        try {
            halda.vloz(obec);
        } catch (HeapException ignored) {
            throw new AgendaKrajException(
                    ChybovaZpravaKraje.NULL_KLIC.getZprava());
        }
    }

    @Override
    public Obec odeberMax() throws AgendaKrajException {
        try {
            return halda.odeberMax();
        } catch (HeapException ex) {
            throw new AgendaKrajException(
                    ChybovaZpravaKraje.PRVEK_NENALEZEN.getZprava());
        }
    }

    @Override
    public boolean jePrazdna() {
        return false;
    }

    @Override
    public int mohutnost() {
        return halda.mohutnost();
    }

    @Override
    public void zrus() { halda.zrus(); }

    @Override
    public Obec zpristupniMax() throws AgendaKrajException {
        try {
            return halda.zpristupniMax();
        } catch (HeapException ex) {
            throw new AgendaKrajException("");
        }
    }

    @Override
    public @NotNull String vypis(ETypProhl typ) {
        try {
            return halda.vypis(typ);
        } catch (HeapException ex) {
            return "";
        }
    }

    @Override
    public Iterator<Obec> vytvorIterator(ETypProhl typ) {
        return halda.vytvorIterator(typ);
    }

    @Override
    public void generuj(int pocet) throws AgendaKrajException {
        if (pocet < 0)
            throw new AgendaKrajException("");
        obecGenerator.generuj(halda, pocet);
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
