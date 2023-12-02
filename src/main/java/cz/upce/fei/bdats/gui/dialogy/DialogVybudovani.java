package cz.upce.fei.bdats.gui.dialogy;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.komponenty.KomponentHalda;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
// </editor-fold>

/**
 * Třída reprezentuje <b>dialog</b> pro vybudování haldy podle pole prvků
 *
 * <p> Rozšiřuje třídu {@link Dialog}
 *
 * <p> Implementuje rozhraní {@link DialogovyKomponent}
 */
public final class DialogVybudovani extends Dialog<ButtonType>
        implements DialogovyKomponent {

    private final Label lPole;
    private final TextField tfPole;

    public DialogVybudovani() {
        this.lPole = new Label(
                dejLabelPodleKriteria());
        this.tfPole = new TextField(
                dejNapoveduPodleKriteria()); // nápověda

        nastavDialog();
    }

    /**
     * Vrátí textovou reprezentaci aktuálně nastaveného kritéria porovnávání prvků v haldě
     *
     * @return Textový řetězec z enumu {@link Titulek}
     */
    private String dejAktualniKriterium() {
        return KomponentHalda.getAktualniKriterium() == Titulek.CB_POCET_OBYVATEL ?
                Titulek.CB_POCET_OBYVATEL.nadpis() : Titulek.CB_NAZEV_OBCE.nadpis();
    }

    /**
     * Vrátí správný label podle aktuálně nastaveného porovnávacího kritéria
     *
     * @return Textový řetězec z enumu {@link Titulek}
     */
    private String dejLabelPodleKriteria() {
        return KomponentHalda.getAktualniKriterium() == Titulek.CB_POCET_OBYVATEL ?
                Titulek.LABEL_POLE_POCTU_OBYVATEL.nadpis() : Titulek.LABEL_POLE_NAZVU_OBCE.nadpis();
    }

    private String dejNapoveduPodleKriteria() {
        return KomponentHalda.getAktualniKriterium() == Titulek.CB_POCET_OBYVATEL ?
                Titulek.PROMPT_POLE_POCTU_OBYVATEL.nadpis() : Titulek.PROMPT_POLE_NAZVU_OBCE.nadpis();
    }

    private void nastavDialog() {
        this.setTitle(
                Titulek.HLAVICKA_DIALOGU_VYBUDOVANI.nadpis());
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
        titulkovyPanel.setText(
                Titulek.HLAVICKA_TITULKOVEHO_PANELU_VYBUDOVANI.nadpis() + dejAktualniKriterium());
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
        gridPane.add(lPole, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(tfPole, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        return gridPane;
    }

// <editor-fold defaultstate="collapsed" desc="Gettery">
    public TextField getTfPole() { return tfPole; }
// </editor-fold>
}
