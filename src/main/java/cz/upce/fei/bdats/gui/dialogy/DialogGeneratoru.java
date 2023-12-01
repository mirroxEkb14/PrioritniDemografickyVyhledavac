package cz.upce.fei.bdats.gui.dialogy;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
// </editor-fold>

/**
 * Třída reprezentuje <b>dialog</b> pro generování prvků
 *
 * <p> Rozšiřuje třídu {@link Dialog}
 *
 * <p> Implementuje rozhraní {@link DialogovyKomponent}
 */
public final class DialogGeneratoru extends Dialog<ButtonType>
        implements DialogovyKomponent {

    private final TextField tfPocet;
    private final Label lPocet;

    public DialogGeneratoru() {
        this.tfPocet = new TextField("10");
        this.lPocet = new Label(Titulek.LABEL_POCET_PRVKU.nadpis());

        nastavDialog();
    }

    private void nastavDialog() {
        this.setTitle(
                Titulek.HLAVICKA_DIALOGU_GENERATOR.nadpis());
        this.setDialogPane(
                this.dejTlacitka(
                        this.getDialogPane()));
        this.getDialogPane().setContent(
                dejTitulkovyPanel());
    }

    /**
     * Vytvoří a vrátí titulkový panel {@link TitledPane}
     *
     * @return Instance {@link TitulkovyPanel}
     */
    private @NotNull TitledPane dejTitulkovyPanel() {
        final TitledPane titulkovyPanel = new TitulkovyPanel();
        titulkovyPanel.setText(Titulek.HLAVICKA_TITULKOVEHO_PANELU_GENERATORU.nadpis());
        titulkovyPanel.setContent(dejGridPane());
        return titulkovyPanel;
    }

    /**
     * Vytvoří a vrátí mřížkový panel {@link GridPane}
     *
     * @return Instace {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(lPocet, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfPocet, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfNazevObce() { return tfPocet; }
// </editor-fold>
}
