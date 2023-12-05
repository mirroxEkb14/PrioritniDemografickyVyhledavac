package cz.upce.fei.bdats.gui.alerty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>zobrazení</i> dialogu typu {@link Alert} s informační
 * zprávou
 *
 * <p> Rozšiřuje třídu {@link Alert}
 */
public final class InfoAlert extends Alert {

// <editor-fold defaultstate="collapsed" desc="Privátní konstanty">
    /**
     * Statická soukromá konstanta reprezentující funkční rozhraní vytváří nový alert typu {@link AlertType#INFORMATION}
     * a zobrazuje ho uživateli s informační zprávou
     */
    private static final Consumer<String> infoLog = t -> {
        final Alert infoOkenko = new InfoAlert(t);
        infoOkenko.showAndWait();
    };

    /**
     * Soukromé konstanty reprezentují výchozí titulek, resp. záhlaví pro všechny informační alerty
     */
    private static final String TITULEK = "Informační Alert";
    private static final String ZAHLAVI = "Info";
    /**
     * Soukromá konstanta reprezentuje maximální délku textového řetězce, po níž se text přesune na další řádek.
     * V případě, že informační zpráva má větší počet znaků, rozsah tohoto dialogu bude podle délky této zprávy
     *
     * @see InfoAlert#InfoAlert(String)
     */
    private static final int MAX_DELKA_RADKU = 55;
// </editor-fold>

    /**
     * <b>Poznámka</b>:
     * <ul>
     * <li> <b>this.getDialogPane().setContent(new Label(zprava)</b>: Ať se zobrazuje celý výpis objektu, přestože
     * je príliš dlouhý
     * </ul>
     */
    public InfoAlert(@NotNull String zprava) {
        super(AlertType.INFORMATION);

        this.setTitle(TITULEK);
        this.setHeaderText(ZAHLAVI);
        if (zprava.length() > MAX_DELKA_RADKU)
            this.getDialogPane().setContent(
                    new Label(zprava));
        else
            this.setContentText(zprava);
    }

    /**
     * Veřejná statická metoda volající {@link InfoAlert#infoLog}
     *
     * @param zprava Informační zpráva
     */
    public static void nahlasInfoLog(String zprava) { infoLog.accept(zprava); }
}
