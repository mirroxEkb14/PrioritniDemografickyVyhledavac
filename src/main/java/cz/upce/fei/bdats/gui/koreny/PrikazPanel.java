package cz.upce.fei.bdats.gui.koreny;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.komponenty.KomponentPrikazy;
import cz.upce.fei.bdats.gui.komponenty.KomponentHalda;
import javafx.scene.layout.VBox;
// </editor-fold>

/**
 * Třída reprezentuje panel s příkazy <i>(pravá část okna)</i> obsahující odkaz na instance seznamu s prvky
 *
 * <p> Rozšiřuje třídu {@link VBox}
 *
 * <p> Třída je vzorem <b>Singleton</b>
 */
public final class PrikazPanel extends VBox {

    private static PrikazPanel instance;

    public static PrikazPanel getInstance() {
        if (instance == null)
            instance = new PrikazPanel();
        return instance;
    }

    private PrikazPanel() { nastavPrikazPanel(); }

    private void nastavPrikazPanel() {
        this.getChildren().addAll(
                KomponentHalda.getInstance(),
                KomponentPrikazy.getInstance());
    }
}
