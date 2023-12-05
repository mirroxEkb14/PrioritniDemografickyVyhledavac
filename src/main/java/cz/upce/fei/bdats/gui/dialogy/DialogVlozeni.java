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
 * Třída reprezentuje <b>dialog</b> pro vkládání nových údajů o obci
 *
 * <p> Rozšiřuje třídu {@link Dialog}
 *
 * <p> Implementuje rozhraní {@link DialogovyKomponent}
 */
public final class DialogVlozeni extends Dialog<ButtonType>
        implements DialogovyKomponent {

    private final TextField tfCislo, tfNazevKraje, tfNazevObce, tfPSC, tfPocetMuzu, tfPocetZen;
    private final Label lCislo, lNazevKraje, lNazevObce, lPSC, lPocetMuzu, lPocetZen;

    public DialogVlozeni() {
        this.tfCislo = new TextField("10");
        this.tfNazevKraje = new TextField("Pardubicky");
        this.tfNazevObce = new TextField();
        this.tfPSC = new TextField("1");
        this.tfPocetMuzu = new TextField();
        this.tfPocetZen = new TextField();
        this.lCislo = new Label(
                Titulek.LABEL_CISLO_KRAJE.nadpis());
        this.lNazevKraje = new Label(
                Titulek.LABEL_NAZEV_KRAJE.nadpis());
        this.lNazevObce = new Label(
                Titulek.LABEL_NAZEV_OBCE.nadpis());
        this.lPSC = new Label(
                Titulek.LABEL_PSC.nadpis());
        this.lPocetMuzu = new Label(
                Titulek.LABEL_MUZE.nadpis());
        this.lPocetZen = new Label(
                Titulek.LABEL_ZENY.nadpis());

        nastavDialog();
    }

    private void nastavDialog() {
        this.setTitle(
                Titulek.HLAVICKA_DIALOG_VLOZENI.nadpis());
        this.setDialogPane(
                this.dejTlacitka(
                        this.getDialogPane()));
        this.getDialogPane().setContent(dejTitulkovyPanel());
    }

    /**
     * Vytvoří a vrátí titulkový panel {@link TitledPane}
     *
     * @return Instance {@link TitulkovyPanel}
     */
    private @NotNull TitledPane dejTitulkovyPanel() {
        final TitledPane titulkovyPanel = new TitulkovyPanel();
        titulkovyPanel.setText(
                Titulek.HLAVICKA_TITULKOVEHO_PANELU_VLOZENI.nadpis());
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
        gridPane.add(lCislo, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfCislo, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(lNazevKraje, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(tfNazevKraje, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(lNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(tfNazevObce, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(lPSC, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(tfPSC, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(lPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PATY);
        gridPane.add(tfPocetMuzu, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PATY);
        gridPane.add(lPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_SESTY);
        gridPane.add(tfPocetZen, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_SESTY);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfCislo() { return tfCislo; }

    public TextField getTfNazevKraje() { return tfNazevKraje; }

    public TextField getTfNazevObce() { return tfNazevObce; }

    public TextField getTfPSC() { return tfPSC; }

    public TextField getTfPocetMuzu() { return tfPocetMuzu; }

    public TextField getTfPocetZen() { return tfPocetZen; }
// </editor-fold>
}
