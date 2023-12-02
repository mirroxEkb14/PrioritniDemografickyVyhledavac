package cz.upce.fei.bdats.gui.koreny;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.SeznamPanelException;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.agenda.IAgendaKraj;
import javafx.collections.ObservableList;
import cz.upce.fei.bdats.generator.Generator;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;
// </editor-fold>

/**
 * Rozhraní definuje sadu základních operací pro <i>správu</i> seznamu {@link ListView}
 *
 * @param <E> Generický typ, jenž bude nahrazen typem prvků uchovávaných v seznamu
 */
public interface ISeznamPanel<E> {

    /**
     * Vybuduje prioritní frontu podle požadovaného pole prvků
     *
     * @param pole Pole prvků
     *
     * @throws SeznamPanelException Když se vystaví {@link AgendaKrajException}
     */
    void vybuduj(E[] pole) throws SeznamPanelException;

    /**
     * Reorganizuje prioritní frontu podle nového kritéria priority
     *
     * @param komp Komparátor pro porovnávání prvků haldy
     *
     * @throws SeznamPanelException Když se vystaví {@link AgendaKrajException}
     */
    void reorganizuj(Comparator<E> komp) throws SeznamPanelException;

    /**
     * Vloží instanci objektu do DS prioritní fronta
     *
     * @param prvek Instance na objekt, jenž bude vložen do seznamu a DS
     *
     * @throws SeznamPanelException Když se vystaví {@link AgendaKrajException}
     *
     * @see ObservableList#add(Object)
     * @see IAgendaKraj#vloz(Object)
     */
    void vloz(E prvek) throws SeznamPanelException;

    /**
     * Přidá určitý počet instancí objektu do seznamu, resp. do DS prioritní fronta
     *
     * @param pocet Počet instancí objektu
     *
     * @throws CeleKladneCisloException Když vstupní celkový počet prvků pro vložení je menší než {@code 0}
     *
     * @see Generator#generuj(IAbstrHeap, int)
     */
    void obnovSeznam(int pocet) throws CeleKladneCisloException;

    /**
     * Odebere prvek s největší prioritou ze seznamu {@link ListView} a zároveň z DS binární halda
     *
     * @throws SeznamPanelException Když se vystaví výjimka {@link AgendaKrajException}
     *
     * @see ObservableList#remove(Object)
     * @see IAgendaKraj#odeberMax()
     */
    void smazMax() throws SeznamPanelException;

    /**
     * Zkontroluje, zda je seznam {@link ListView} prázdný
     *
     * @return {@code true} pokud není seznam prázdný, jinak {@code false}
     *
     * @see ObservableList#isEmpty()
     */
    boolean jePrazdny();

    /**
     * Vrátí aktuální počet prvků v prioritní frontě
     *
     * @return Počet prvků v haldě
     *
     * @see IAgendaKraj#mohutnost()
     */
    int mohutnost();

    /**
     * Zruší seznam {@link ListView} a zároveň DS prioritní frontu
     *
     * @see ObservableList#clear()
     * @see IAgendaKraj#zrus()
     */
    void vyprazdni();

    /**
     * Uloží aktuální stav seznamu {@link ListView}, vyčistí ho a vypíše posloupnost prvků prioritní fronty
     * podle v určitém pořadí v textové podobě
     *
     * @param typ Typ prohlížení
     *
     * @throws SeznamPanelException Když se vystaví výjimka {@link AgendaKrajException}
     *
     * @see IAgendaKraj#vypis(ETypProhl)
     */
    void vypisHaldu(ETypProhl typ) throws SeznamPanelException;

    /**
     * Vyčistí seznam {@link ListView} zobrazující posloupnost haldy a přidá všechny prvky z uložiště do seznamu
     *
     * @see ObservableList#addAll(Collection)
     */
    void schovejHaldu();

    /**
     * Načte objekty do DS prioritní fronta
     *
     * @param cesta Cesta k souboru
     *
     * @return {@code true} pokud načtení proběhlo bez vyhození i/o výjimek, jinak {@code false}
     *
     * @see IAgendaKraj#nacti(String)
     */
    boolean nacti(String cesta);

    /**
     * Uloží aktuální stav prioritní fronty do souboru
     *
     * @return {@code true} pokud uložení proběhlo bez vyhození i/o výjimek, jinak {@code false}
     *
     * @see IAgendaKraj#uloz()
     */
    boolean uloz();

    /**
     * Nastavuje výchozí vzhled a chování seznam panelu
     *
     * @param seznamPanel Instance na {@link ListView}, jenž musí být nastaven
     */
    default void nastavSeznamPanel(@NotNull ListView<E> seznamPanel) {
        seznamPanel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        seznamPanel.setMinWidth(660);
        seznamPanel.setCellFactory(cell -> new ListCell<>() {
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
