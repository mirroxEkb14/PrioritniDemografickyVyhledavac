package cz.upce.fei.bdats.gui.alerty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import javafx.scene.control.Alert;

import java.util.function.Consumer;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>zobrazení</i> dialogu typu {@link Alert} s informační
 * zprávou
 *
 * <p> Rozšiřuje třídu {@link Alert}
 */
public final class InfoAlert extends Alert {

    /**
     * Statická soukromá konstanta reprezentující funkční rozhraní vytváří nový alert typu {@link AlertType#INFORMATION}
     * a zobrazuje ho uživateli s informační zprávou
     */
    private static final Consumer<String> infoLog = t -> {
        final Alert infoOkenko = new InfoAlert(t);
        infoOkenko.showAndWait();
    };

    public InfoAlert(String zprava) {
        super(AlertType.INFORMATION);

        this.setTitle("Informační Alert"); // titulek
        this.setHeaderText("Info"); // záhlaví
        this.setContentText(zprava);
    }

    /**
     * Veřejná statická metoda volající {@link InfoAlert#infoLog}
     *
     * @param zprava Informační zpráva
     */
    public static void nahlasInfoLog(String zprava) { infoLog.accept(zprava); }
}
