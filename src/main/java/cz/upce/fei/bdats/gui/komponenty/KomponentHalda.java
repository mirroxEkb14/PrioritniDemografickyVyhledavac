package cz.upce.fei.bdats.gui.komponenty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.alerty.ErrorAlert;
import cz.upce.fei.bdats.gui.alerty.InfoAlert;
import cz.upce.fei.bdats.gui.dialogy.*;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdats.gui.koreny.ISeznamPanel;
import cz.upce.fei.bdats.gui.koreny.SeznamPanel;
import cz.upce.fei.bdats.gui.tvurce.TvurceObce;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.zpravy.LogZprava;
import cz.upce.fei.bdats.halda.IAbstrHeap;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
// </editor-fold>

/**
 * Třída implementuje sadu operací pro <i>ovládání</i> tlačítek, jež pracují se základními operacemi prioritní
 * fronty
 *
 * <p> Třída je vzorem <b>Singleton</b>
 *
 * <p> Rozšiřuje třídu {@link TitulkovyPanel}
 *
 * @see IAbstrHeap
 */
public final class KomponentHalda extends TitulkovyPanel {

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    private final Button vybudujBtn, reorganizujBtn, vlozBtn, odeberMaxBtn, prazdnostBtn, zrusBtn, zpristupniMaxBtn;
    private final ChoiceBox<String> vypisCb = new ChoiceBox<>();

    private final ISeznamPanel<Obec> seznamPanel = SeznamPanel.getInstance();

    /**
     * Soukromá konstanta reprezentuje funkční rozhraní, jež vytvoří novou instanci výběrového pole {@link ChoiceBox}
     */
    private final BiConsumer<String, String> tvurceCbIteratoru = (t, u) -> {
        this.vypisCb.getItems().clear();

        if (t == null || t.isEmpty())
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.nadpis(), u);
        else if (u == null || u.isEmpty())
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.nadpis(), t);
        else
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.nadpis(), t, u);

        this.vypisCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        this.vypisCb.getSelectionModel().select(Titulek.CB_VYPIS.nadpis());
        this.vypisCb.setOnAction(actionEvent -> nastavUdalostVypsani());
    };

// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární metoda">
    private static KomponentHalda instance;

    public static KomponentHalda getInstance() {
        if (instance == null)
            instance = new KomponentHalda();
        return instance;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Nastavení třídy">
    private KomponentHalda() {
        this.vybudujBtn = new Tlacitko(Titulek.BTN_VYBUDUJ.nadpis());
        this.vybudujBtn.setOnAction(actionEvent -> nastavUdalostVybudovani());

        this.reorganizujBtn = new Tlacitko(Titulek.BTN_REORGANIZUJ.nadpis());
        this.reorganizujBtn.setOnAction(actionEvent -> nastavUdalostReorganizovani());
        this.reorganizujBtn.setDisable(true);

        this.vlozBtn = new Tlacitko(Titulek.BTN_VLOZ.nadpis());
        this.vlozBtn.setOnAction(actionEvent -> nastavUdalostVlozeni());

        this.odeberMaxBtn = new Tlacitko(Titulek.BTN_ODEBER_MAX.nadpis());
        this.odeberMaxBtn.setDisable(true);
        this.odeberMaxBtn.setOnAction(actionEvent -> nastavUdalostOdebirani());

        tvurceCbIteratoru.accept(
                Titulek.CB_SIRKA.nadpis(),
                Titulek.CB_HLOUBKA.nadpis());
        this.vypisCb.setDisable(true);

        this.prazdnostBtn = new Tlacitko(Titulek.BTN_PRAZDNOST.nadpis());
        this.prazdnostBtn.setDisable(true);
        this.prazdnostBtn.setOnAction(actionEvent -> nastavUdalostPrazdnosti());

        this.zrusBtn = new Tlacitko(Titulek.BTN_ZRUS.nadpis());
        this.zrusBtn.setDisable(true);
        this.zrusBtn.setOnAction(actionEvent -> nastavUdalostZruseni());

        this.zpristupniMaxBtn = new Tlacitko(Titulek.BTN_ZPRISTUPNI_MAX.nadpis());
        this.zpristupniMaxBtn.setDisable(true);

        nastavKomponentHaldy();
    }

    private void nastavKomponentHaldy() {
        this.setText(
                Titulek.KOMPONENT_HALDA.nadpis());
        this.setContent(dejGridPane());
    }

    /**
     * Vytvoří a vrátí mřížkový panel {@link GridPane}
     *
     * @return Instace {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(vybudujBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(reorganizujBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(vlozBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(odeberMaxBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        gridPane.add(prazdnostBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(zrusBtn, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_TRETI);
        gridPane.add(zpristupniMaxBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        gridPane.add(vypisCb, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_CTVRTY);
        return gridPane;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: vybuduj(Obec pole[])">
    private void nastavUdalostVybudovani() {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: reorganizuj(Comparator<Obec> komp)">
    private void nastavUdalostReorganizovani() {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: vloz(Obec obec)">
    private void nastavUdalostVlozeni() {
        final DialogVlozeni dialog = new DialogVlozeni();
        final Optional<ButtonType> odpoved = dialog.showAndWait();
        if (odpoved.isPresent() && dialog.jeTlacitkoOk(odpoved.get())) {
            final String klic = dialog.getTfNazevObce().getText();
            if (klic.isEmpty()) {
                ErrorAlert.nahlasErrorLog(
                        LogZprava.LOG_TVORENI_PRAZDNY_KLIC.getZprava());
                return;
            }
            new TvurceObce().vytvor(dialog)
                    .ifPresentOrElse(
                            novaObec -> {
                                seznamPanel.vloz(novaObec);
                                obnovTlacitkaProVlozeni();
                            },
                            () -> ErrorAlert.nahlasErrorLog(
                                    LogZprava.SPATNA_POLE.getZprava()));

        }
    }

    private void obnovTlacitkaProVlozeni() {
        if (jeVypnutoBtnVybuduj()) zapniBtnVybuduj();
        if (jeVypnutoBtnReprganizuj()) zapniBtnReorganizuj();
        if (jeVypnutoBtnOdeberMax()) zapniBtnOdeberMax();
        if (jeVypnutoBtnPrazdnost()) zapniBtnPrazdnost();
        if (jeVypnutoBtnZrus()) zapniBtnZrus();
        if (jeVypnutoBtnVypis()) zapniBtnVypis();
        if (jeVypnutoBtnZpristupniMax()) zapniBtnZpristupniMax();
        if (KomponentPrikazy.getInstance().jeVypnutoBtnUloz())
            KomponentPrikazy.getInstance().zapniBtnUloz();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: odeberMax()">
    private void nastavUdalostOdebirani() {
        seznamPanel.smazMax();
        obnovTlacitkaProOdebirani();
    }

    private void obnovTlacitkaProOdebirani() {
        if (seznamPanel.jePrazdny()) {
            vypniBtnReorganizuj();
            vypniBtnOdeberMax();
            vypniBtnVypis();
            vypniBtnPrazdnost();
            vypniBtnZrus();
            KomponentPrikazy.getInstance().vypniBtnUloz();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: vypis(ETypProhl typ)">
    private void nastavUdalostVypsani() {
        final String zvolenaAkce = vypisCb.getSelectionModel().getSelectedItem();
        if (jeVybranaAkceProProhlizeni(zvolenaAkce)) {
            final ETypProhl typ = dejVybranyTyp(zvolenaAkce);
            seznamPanel.vypisHaldu(typ);
            obnovTlacitkaProIterator();
        } else if (jeVybranaAkceProVraceni(zvolenaAkce)) {
            seznamPanel.schovejHaldu();
            obnovTlacitkaProVrat();
            this.vypisCb.getSelectionModel().select(
                    Titulek.CB_VYPIS.nadpis());
        }
    }

    private void obnovTlacitkaProIterator() {
        vypniBtnVybuduj();
        vypniBtnReorganizuj();
        vypniBtnVloz();
        vypniBtnOdeberMax();
        vypniBtnPrazdnost();
        vypniBtnZrus();
        vypniBtnZpristupniMax();
        KomponentPrikazy.getInstance().vypniBtnGeneruj();
        KomponentPrikazy.getInstance().vypniBtnNacti();
        KomponentPrikazy.getInstance().vypniBtnUloz();

        tvurceCbIteratoru.accept(
                Titulek.CB_VRAT.nadpis(),
                null);
    }

    private void obnovTlacitkaProVrat() {
        zapniBtnVybuduj();
        zapniBtnReorganizuj();
        zapniBtnVloz();
        zapniBtnOdeberMax();
        zapniBtnPrazdnost();
        zapniBtnZrus();
        vypniBtnZpristupniMax();
        KomponentPrikazy.getInstance().zapniBtnGeneruj();
        KomponentPrikazy.getInstance().zapniBtnNacti();
        KomponentPrikazy.getInstance().zapniBtnUloz();

        tvurceCbIteratoru.accept(
                Titulek.CB_SIRKA.nadpis(),
                Titulek.CB_HLOUBKA.nadpis());
    }

    /**
     * Zjišťuje, zda je daná položka určena pro prohlížení haldy
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true} pokud je položka pro prohlížení <i>(šířka/hloubka)</i>, jinak {@code false} <i>(vrať)</i>
     */
    private boolean jeVybranaAkceProProhlizeni(String polozka) {
        return Titulek.CB_SIRKA.nadpis().equalsIgnoreCase(polozka)
                || Titulek.CB_HLOUBKA.nadpis().equalsIgnoreCase(polozka);
    }

    /**
     * Vrací typ prohlížení na základě zvolené položky
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return Typ prohlížení
     *
     * @see ETypProhl
     */
    private ETypProhl dejVybranyTyp(@NotNull String polozka) {
        return polozka.equalsIgnoreCase(Titulek.CB_SIRKA.nadpis())
                ? ETypProhl.SIRKA : ETypProhl.HLOUBKA;
    }

    /**
     * Zjišťuje, zda je daná položka určena pro návrat
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true} pokud je položka pro návrat <i>(vrať)</i>, jinak {@code false} <i>(šířka/hloubka)</i>
     */
    private boolean jeVybranaAkceProVraceni(String polozka) {
        return Titulek.CB_VRAT.nadpis().equalsIgnoreCase(polozka);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: jePrazdna()">
    private void nastavUdalostPrazdnosti() {
        InfoAlert.nahlasInfoLog(
                String.valueOf(seznamPanel.mohutnost()));
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: zrus()">
    private void nastavUdalostZruseni() {
        seznamPanel.vyprazdni();
        obnovTlacitkaProZruseni();
    }

    private void obnovTlacitkaProZruseni() {
        if (jeVypnutoBtnVloz()) zapniBtnVloz();

        vypniBtnReorganizuj();
        vypniBtnZpristupniMax();
        vypniBtnOdeberMax();
        vypniBtnPrazdnost();
        vypniBtnZrus();
        vypniBtnVypis();
        KomponentPrikazy.getInstance().vypniBtnUloz();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné Metody: Kontrola stavu tlačítek">
    public boolean jeVypnutoBtnVybuduj() { return vybudujBtn.isDisabled(); }
    public boolean jeVypnutoBtnReprganizuj() { return reorganizujBtn.isDisabled(); }
    public boolean jeVypnutoBtnVloz() { return vlozBtn.isDisabled(); }
    public boolean jeVypnutoBtnOdeberMax() { return odeberMaxBtn.isDisabled(); }
    public boolean jeVypnutoBtnPrazdnost() { return prazdnostBtn.isDisabled(); }
    public boolean jeVypnutoBtnZrus() { return zrusBtn.isDisabled(); }
    public boolean jeVypnutoBtnVypis() { return vypisCb.isDisabled(); }
    public boolean jeVypnutoBtnZpristupniMax() { return zpristupniMaxBtn.isDisabled(); }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné Metody: Přepínače">
    public void zapniBtnVybuduj() { vybudujBtn.setDisable(false); }
    public void vypniBtnVybuduj() { vybudujBtn.setDisable(true); }

    public void zapniBtnReorganizuj() { reorganizujBtn.setDisable(false); }
    public void vypniBtnReorganizuj() { reorganizujBtn.setDisable(true); }

    public void zapniBtnVloz() { vlozBtn.setDisable(false); }
    public void vypniBtnVloz() { vlozBtn.setDisable(true); }

    public void zapniBtnOdeberMax() { odeberMaxBtn.setDisable(false); }
    public void vypniBtnOdeberMax() { odeberMaxBtn.setDisable(true); }

    public void zapniBtnVypis() { vypisCb.setDisable(false); }
    public void vypniBtnVypis() { vypisCb.setDisable(true); }

    public void zapniBtnPrazdnost() { prazdnostBtn.setDisable(false); }
    public void vypniBtnPrazdnost() { prazdnostBtn.setDisable(true); }

    public void zapniBtnZrus() { zrusBtn.setDisable(false); }
    public void vypniBtnZrus() { zrusBtn.setDisable(true); }

    public void zapniBtnZpristupniMax() { zpristupniMaxBtn.setDisable(false); }
    public void vypniBtnZpristupniMax() { zpristupniMaxBtn.setDisable(true); }
// </editor-fold>
}
