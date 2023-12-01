package cz.upce.fei.bdats.gui.koreny;

import javafx.scene.layout.HBox;

/**
 * Třída reprezentuje kořenový uzel skládající se ze dvou části:
 * <ol>
 * <li> {@link SeznamPanel}: Seznam prvků haldy v pořadí jejich vložení, resp. načtení <i>(levá část okna)</i>
 * <li> {@link PrikazPanel}: Panel s tlačítky pro správu seznamu <i>(pravá část okna)</i>
 * </ol>
 */
public final class Okno extends HBox {

    private final SeznamPanel seznam;
    private final PrikazPanel panelPrikazu;

    public Okno() {
        seznam = SeznamPanel.getInstance();
        panelPrikazu = PrikazPanel.getInstance();
        nastavOkno();
    }

    private void nastavOkno() { this.getChildren().addAll(seznam, panelPrikazu); }
}
