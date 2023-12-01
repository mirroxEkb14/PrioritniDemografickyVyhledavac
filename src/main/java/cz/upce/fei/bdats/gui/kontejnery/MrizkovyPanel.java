package cz.upce.fei.bdats.gui.kontejnery;

// <editor-fold defaultstate="collapsed" desc="Importy">
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
// </editor-fold>

/**
 * Třída reprezentuje <b>mřížkový panel</b> pro uspořádání komponent
 *
 * <p> Rozšiřuje třídu {@link GridPane}
 */
public class MrizkovyPanel extends GridPane {

    public final double ODSAZENI_OKRAJE = 10.0;
    public final double VERTIKALNI_MEZERA = 5.0;
    public final double HORIZONTALNI_MEZERA = 5.0;

    /**
     * Veřejná konstanta určuje počet sloupců, jež má zabírat prvek na jednotlivém rádku mřížkového panelu
     *
     * @see GridPane#setColumnSpan(Node, Integer)
     */
    public static final int ROZPETI_SLOUPCU = 2;
    /**
     * Veřejné konstanty reprezentují sloupcové <i>(columnIndex)</i> a řádkové <i>(rowIndex)</i> indexy pro
     * umístění v mřížce
     */
    public static final int SLOUPCOVY_INDEX_PRVNI = 0;
    public static final int RADKOVY_INDEX_PRVNI = 0;
    public static final int SLOUPCOVY_INDEX_DRUHY = 1;
    public static final int RADKOVY_INDEX_DRUHY = 1;
    public static final int RADKOVY_INDEX_TRETI = 2;
    public static final int RADKOVY_INDEX_CTVRTY = 3;
    public static final int RADKOVY_INDEX_PATY = 4;
    public static final int RADKOVY_INDEX_SESTY = 5;

    public MrizkovyPanel() {
        this.setPadding(new Insets(ODSAZENI_OKRAJE));
        this.setVgap(VERTIKALNI_MEZERA);
        this.setHgap(HORIZONTALNI_MEZERA);
    }
}
