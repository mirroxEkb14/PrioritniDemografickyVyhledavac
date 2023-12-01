package cz.upce.fei.bdats.gui.alerty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import javafx.scene.control.Alert;

import java.util.function.Consumer;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>zobrazení</i> dialogu typu {@link Alert} s chybovou
 * zprávou
 *
 * <p> Rozšiřuje třídu {@link Alert}
 */
public final class ErrorAlert extends Alert {

    /**
     * Statická soukromá konstanta vytváří nový alert typu {@link AlertType#ERROR} a zobrazuje ho uživateli se
     * zprávou o chybě
     */
    private static final Consumer<String> errorLog = t -> {
        final Alert chyboveOkenko = new ErrorAlert(t);
        chyboveOkenko.showAndWait();
    };

    public ErrorAlert(String zprava) {
        super(AlertType.ERROR);

        this.setTitle("Chybový Alert"); // titulek
        this.setHeaderText("Chyba"); // záhlaví
        this.setContentText(zprava);
    }

    /**
     * Veřejná statická metoda volající {@link ErrorAlert#errorLog}
     *
     * @param zprava Zpráva o chybě
     */
    public static void nahlasErrorLog(String zprava) { errorLog.accept(zprava); }
}
