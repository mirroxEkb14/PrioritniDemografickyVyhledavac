package cz.upce.fei.bdats.gui.dialogy;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.jetbrains.annotations.NotNull;
// </editor-fold>

/**
 * Rozhraní definuje a implementuje sadu výchozích obecných operací pro vytvoření dialogů, jež platí pro
 * všechny dialogy implementující toto rozhraní
 */
public interface DialogovyKomponent {

    /**
     * Nastaví výchozí stav pro tlačítka instance dialogu (předanému v argumentu metody)
     *
     * @param dialogovyPanel Instance dialogu, jehož tlačítka {@link ButtonType#OK} a {@link ButtonType#CANCEL}
     *                       budou nastaveny
     *
     * @return Instance na ten samý dialogový panel {@link DialogPane}
     */
    default DialogPane dejTlacitka(@NotNull DialogPane dialogovyPanel) {
        final ButtonType okTlacitko = new ButtonType(
                Titulek.TLACITKO_FAJN.nadpis(), ButtonBar.ButtonData.OK_DONE);
        final ButtonType cancelTlacitko = new ButtonType(
                Titulek.TLACITKO_ZRUSIT.nadpis(), ButtonBar.ButtonData.CANCEL_CLOSE);

        dialogovyPanel.getButtonTypes().addAll(okTlacitko, cancelTlacitko);
        return dialogovyPanel;
    }

    /**
     * Zjistí, zda bylo stisknuto tlačítko typu {@link ButtonType#OK}
     *
     * @param odpoved Typ tlačítka reprezentující jeho konkrétní druh
     *
     * @return {@code true} pokud bylo stisknuto {@link ButtonType#OK}, jinak {@code false}
     */
    default boolean jeTlacitkoOk(@NotNull ButtonType odpoved) { return odpoved.getButtonData().isDefaultButton(); }
}
