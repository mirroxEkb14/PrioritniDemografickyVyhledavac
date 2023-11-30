package cz.upce.fei.bdats.gui.koreny;

import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

/**
 * @author amirov 11/29/2023
 * @project semestralni_prace_c_amirov
 */
public interface ISeznamPanel<E> {

    void pridej(Obec obec);

    void obnovSeznam(int pocet);

    void vymaz();

    void vypisHaldu(ETypProhl typ);

    boolean nacti(String cesta);

    boolean uloz();

    void vyprazdni();

    void schovejStrom();

    boolean jePrazdny();

    int dejMohutnost();

    default void nastavSeznamPanel(@NotNull ListView<E> seznamPanel) {
        seznamPanel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        seznamPanel.setMinWidth(660);
        seznamPanel.setCellFactory(cell -> new ListCell<E>() {
            @Override
            protected void updateItem(E item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item.toString());
                    setFont(Font.font("Monospaced", 13));
                } else {
                    setText("");
                }
            }
        });
    }
}
