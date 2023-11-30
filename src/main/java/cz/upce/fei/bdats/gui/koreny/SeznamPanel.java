package cz.upce.fei.bdats.gui.koreny;

import cz.upce.fei.bdats.agenda.AgendaKraj;
import cz.upce.fei.bdats.agenda.IAgendaKraj;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class SeznamPanel extends ListView<String> implements ISeznamPanel<String> {

    private final IAgendaKraj<Obec> agendaKraj = AgendaKraj.getInstance();
    private final ListView<String> ulozenyStav = new ListView<>();

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static SeznamPanel instance;

    public static SeznamPanel getInstance() {
        if (instance == null)
            instance = new SeznamPanel();
        return instance;
    }
// </editor-fold>

    private SeznamPanel() { this.nastavSeznamPanel(this); }

    @Override
    public void pridej(Obec obec) {
        try {
            agendaKraj.vloz(obec);
            pridejPrvek(obec);
        } catch (AgendaKrajException ignored) {}
    }

    @Override
    public void obnovSeznam(int pocet) {
        try {
            agendaKraj.generuj(pocet);
            vycistiSeznam();
            pridejHalduDoSeznamu();
        } catch (AgendaKrajException ignored) {}
    }

    @Override
    public void vymaz() {
        try {
            vymazPrvek(agendaKraj.odeberMax());
        } catch (AgendaKrajException ignored) {}
    }

    @Override
    public void vypisHaldu(ETypProhl typ) {
        try {
            ulozAktualniStav();
            vycistiSeznam();
            this.getItems().add(agendaKraj.vypis(typ));
        } catch (AgendaKrajException ignored) {}
    }

    @Override
    public boolean nacti(String cesta) {
        if (agendaKraj.nacti(cesta)) {
            pridejHalduDoSeznamu();
            return true;
        }
        return false;
    }

    @Override
    public boolean uloz() { return agendaKraj.uloz(); }

    @Override
    public void vyprazdni() {
        agendaKraj.zrus();
        vycistiSeznam();
    }

    @Override
    public void schovejStrom() { nactiPredchoziStav(); }

    @Override
    public boolean jePrazdny() { return this.getItems().isEmpty(); }

    @Override
    public int dejMohutnost() { return agendaKraj.mohutnost(); }

// <editor-fold defaultstate="collapsed" desc="Privátní metody">
    private void pridejPrvek(@NotNull Obec obec) {
    this.getItems().add(obec.toString());
}

    private void vymazPrvek(@NotNull Obec obec) {
        this.getItems().remove(obec.toString());
    }

    private void vycistiSeznam() { this.getItems().clear(); }

    private void ulozAktualniStav() {
        ulozenyStav.getItems().addAll(this.getItems());
    }

    private void nactiPredchoziStav() {
        vycistiSeznam();
        this.getItems().addAll(ulozenyStav.getItems());
        ulozenyStav.getItems().clear();
    }

    private void pridejHalduDoSeznamu() {
        final Iterator<Obec> iterator = agendaKraj.vytvorIterator(ETypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            pridejPrvek(iterator.next());
        }
    }
// </editor-fold>
}
