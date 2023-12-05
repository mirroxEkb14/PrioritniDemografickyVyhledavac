package cz.upce.fei.bdats.gui.koreny;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.agenda.AgendaKraj;
import cz.upce.fei.bdats.agenda.IAgendaKraj;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.SeznamPanelException;
import cz.upce.fei.bdats.vyjimky.zpravy.SeznamPanelZprava;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>správu</i> seznamu {@link ListView} a umožňuje volat
 * jednotlivé metody z agendy
 *
 * <p> Rozšiřuje třídu {@link ListView}
 * <p> Implementuje rozhraní {@link ISeznamPanel}
 */
public final class SeznamPanel extends ListView<Obec> implements ISeznamPanel<Obec> {

    private final IAgendaKraj<Obec> agendaKraj = AgendaKraj.getInstance();
    private final ListView<Obec> ulozenyStav = new ListView<>();

    private static SeznamPanel instance;

    public static SeznamPanel getInstance() {
        if (instance == null)
            instance = new SeznamPanel();
        return instance;
    }

    private SeznamPanel() { this.nastavSeznamPanel(this); }

    @Override
    public void vybuduj(Obec[] pole) throws SeznamPanelException {
        try {
            agendaKraj.vybuduj(pole);
        } catch (SeznamPanelException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_VSTUP_POLE.getZprava());
        }
    }

    @Override
    public void reorganizuj(Comparator<Obec> komp) throws SeznamPanelException {
        try {
            agendaKraj.reorganizuj(komp);
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_VSTUP_KOMPARATORU.getZprava());
        }
    }

    @Override
    public void vloz(Obec obec) throws SeznamPanelException {
        try {
            agendaKraj.vloz(obec);
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_VSTUP_OBCE.getZprava());
        }
    }

    @Override
    public void obnovSeznam(int pocet) throws SeznamPanelException {
        try {
            agendaKraj.generuj(pocet);
        } catch (CeleKladneCisloException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.NEPLATNE_CELE_CISLO.getZprava());
        }
    }

    @Override
    public Obec odeberMax() throws SeznamPanelException {
        try {
            return agendaKraj.odeberMax();
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_SEZNAM.getZprava());
        }
    }

    @Override
    public Obec zpristupniMax() throws SeznamPanelException {
        try {
            return agendaKraj.zpristupniMax();
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_SEZNAM.getZprava());
        }
    }

    @Override
    public boolean jeHaldaPrazdna() { return agendaKraj.jePrazdna(); }

    @Override
    public int mohutnostHaldy() { return agendaKraj.mohutnost(); }

    @Override
    public void vyprazdni() {
        agendaKraj.zrus();
        zrusSeznamPanel();
    }

    @Override
    public void vypisHaldu(ETypProhl typ) throws SeznamPanelException {
        try {
            ulozAktualniStav();
            zrusSeznamPanel();
            pridejHalduDoSeznamu(typ);
        } catch (AgendaKrajException ex) {
            throw new SeznamPanelException(
                    SeznamPanelZprava.PRAZDNY_VSTUP_TYPU.getZprava());
        }
    }

    @Override
    public void schovejHaldu() { nactiPredchoziHaldu(); }

    @Override
    public boolean nacti(String cesta) {
        return agendaKraj.nacti(cesta);
    }

    @Override
    public boolean uloz() { return agendaKraj.uloz(); }

// <editor-fold defaultstate="collapsed" desc="Privátní metody">
    /**
     * @param obec Instance obce pro vložení do seznamu {@link ListView}
     *
     * @see SeznamPanel#pridejHalduDoSeznamu(ETypProhl)
     */
    private void pridej(@NotNull Obec obec) {
        this.getItems().add(obec);
    }

    /**
     * @see SeznamPanel#vyprazdni()
     * @see SeznamPanel#vypisHaldu(ETypProhl)
     * @see SeznamPanel#nactiPredchoziHaldu()
     */
    private void zrusSeznamPanel() { this.getItems().clear(); }

    /**
     * @see SeznamPanel#vypisHaldu(ETypProhl)
     */
    private void ulozAktualniStav() {
        ulozenyStav.getItems().addAll(this.getItems());
    }

    /**
     * @see SeznamPanel#schovejHaldu()
     */
    private void nactiPredchoziHaldu() {
        zrusSeznamPanel();
        this.getItems().addAll(ulozenyStav.getItems());
        ulozenyStav.getItems().clear();
    }

    /**
     * @see SeznamPanel#nacti(String)
     */
    private void pridejHalduDoSeznamu(ETypProhl typ) {
        final Iterator<Obec> iterator = agendaKraj.vytvorIterator(typ);
        while (iterator.hasNext()) {
            pridej(iterator.next());
        }
    }
// </editor-fold>
}
