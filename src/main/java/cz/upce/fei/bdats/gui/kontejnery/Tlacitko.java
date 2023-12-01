package cz.upce.fei.bdats.gui.kontejnery;

import javafx.scene.control.Button;

/**
 * Třída reprezentuje <b>tlačítko</b> obsahující s výchozím nastavením pro všechny potomky
 *
 * Rozšiřuje třídu {@link Button}
 */
public final class Tlacitko extends Button {

    private static final double PREFEROVANA_SIRKA_TLACITKA = 100.0;

    public Tlacitko(String btnText) {
        this.setText(btnText);
        this.setPrefWidth(PREFEROVANA_SIRKA_TLACITKA);
    }

    public static double getPrefSirka() { return PREFEROVANA_SIRKA_TLACITKA; }
}
