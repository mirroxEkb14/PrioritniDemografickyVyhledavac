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

// <editor-fold defaultstate="collapsed" desc="Privátní konstanty">
    /**
     * Statická soukromá konstanta reprezentující funkční rozhraní vytváří nový alert typu {@link AlertType#ERROR}
     * a zobrazuje ho uživateli se zprávou o chybě
     */
    private static final Consumer<String> errorLog = t -> {
        final Alert chyboveOkenko = new ErrorAlert(t);
        chyboveOkenko.showAndWait();
    };

    /**
     * Soukromé konstanty reprezentují výchozí titulek, resp. záhlaví pro všechny chybové alerty
     */
    private static final String TITULEK = "Chybový Alert";
    private static final String ZAHLAVI = "Chyba";
// </editor-fold>

    public ErrorAlert(String zprava) {
        super(AlertType.ERROR);

        this.setTitle(TITULEK);
        this.setHeaderText(ZAHLAVI);
        this.setContentText(zprava);
    }

    /**
     * Veřejná statická metoda volající {@link ErrorAlert#errorLog}
     *
     * @param zprava Zpráva o chybě
     */
    public static void nahlasErrorLog(String zprava) { errorLog.accept(zprava); }
}
