package cz.upce.fei.bdats.gui.kontejnery;

// <editor-fold defaultstate="collapsed" desc="Importy">
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
// </editor-fold>

/**
 * Třída reprezentuje <b>titulkový panel</b> obsahující mřížkový panel s výchozím nastavením pro vnitřní
 * grafické prvky (tlačítka, výběrová pole atd.)
 *
 <p> Rozšiřuje třídu {@link TitledPane}
 */
public class TitulkovyPanel extends TitledPane {

    /**
     * Členská konstanta reprezentuje výchozí šířku výběrového pole {@link ChoiceBox} pro tento komponent
     *
     * <p> Je přístupná pouze v rámci stejného balíčku <i>(package)</i> nebo pro podtřídy <i>(dědičnost)</i>
     */
    protected static final double PREFEROVANA_SIRKA_POLE = 100.0;

    private static final boolean JE_ROZLOZEN = true;
    private static final boolean JE_SKLADAN = false;
    private static final boolean JE_ANIMOVANY = true;

    public TitulkovyPanel() {
        this.setExpanded(JE_ROZLOZEN);
        this.setCollapsible(JE_SKLADAN);
        this.setAnimated(JE_ANIMOVANY);
    }
}
