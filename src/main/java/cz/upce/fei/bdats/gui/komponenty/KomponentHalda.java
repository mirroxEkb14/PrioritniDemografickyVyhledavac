package cz.upce.fei.bdats.gui.komponenty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.alerty.ErrorAlert;
import cz.upce.fei.bdats.gui.alerty.InfoAlert;
import cz.upce.fei.bdats.gui.dialogy.*;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdats.gui.koreny.SeznamPanel;
import cz.upce.fei.bdats.gui.tvurce.TvurceObce;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.zpravy.ZpravaLogu;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
// </editor-fold>

public final class KomponentHalda extends TitulkovyPanel {

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    private final Button vybudujBtn, reorganizujBtn, vlozBtn, odeberMaxBtn, prazdnostBtn, zrusBtn, zpristupniMaxBtn;

    private final ChoiceBox<String> vypisCb = new ChoiceBox<>();
    final BiConsumer<String, String> tvurceCbIteratoru = (t, u) -> {
        this.vypisCb.getItems().clear();

        if (t == null || t.isEmpty())
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.getNadpis(), u);
        else if (u == null || u.isEmpty())
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.getNadpis(), t);
        else
            this.vypisCb.getItems().addAll(
                    Titulek.CB_VYPIS.getNadpis(), t, u);

        this.vypisCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        this.vypisCb.getSelectionModel().select(Titulek.CB_VYPIS.getNadpis());
        this.vypisCb.setOnAction(actionEvent -> nastavUdalostVypsani());
    };

    private final SeznamPanel seznamPanel = SeznamPanel.getInstance();
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static KomponentHalda instance;

    public static KomponentHalda getInstance() {
        if (instance == null)
            instance = new KomponentHalda();
        return instance;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Nastavení třídy">
    /**
     * Privátní konstruktor inicializující tlačítka a nastavující události
     */
    private KomponentHalda() {
        this.vybudujBtn = new Tlacitko(Titulek.BTN_VYBUDUJ.getNadpis());
        this.vybudujBtn.setOnAction(actionEvent -> nastavUdalostVybudovani());

        this.reorganizujBtn = new Tlacitko(Titulek.BTN_REORGANIZUJ.getNadpis());
        this.reorganizujBtn.setOnAction(actionEvent -> nastavUdalostReorganizovani());
        this.reorganizujBtn.setDisable(true);

        this.vlozBtn = new Tlacitko(Titulek.BTN_VLOZ.getNadpis());
        this.vlozBtn.setOnAction(actionEvent -> nastavUdalostVlozeni());

        this.odeberMaxBtn = new Tlacitko(Titulek.BTN_ODEBER_MAX.getNadpis());
        this.odeberMaxBtn.setDisable(true);
        this.odeberMaxBtn.setOnAction(actionEvent -> nastavUdalostOdebirani());

        tvurceCbIteratoru.accept(
                Titulek.CB_SIRKA.getNadpis(),
                Titulek.CB_HLOUBKA.getNadpis());
        this.vypisCb.setDisable(true);

        this.prazdnostBtn = new Tlacitko(Titulek.BTN_PRAZDNOST.getNadpis());
        this.prazdnostBtn.setDisable(true);
        this.prazdnostBtn.setOnAction(actionEvent -> nastavUdalostPrazdnosti());

        this.zrusBtn = new Tlacitko(Titulek.BTN_ZRUS.getNadpis());
        this.zrusBtn.setDisable(true);
        this.zrusBtn.setOnAction(actionEvent -> nastavUdalostZruseni());

        this.zpristupniMaxBtn = new Tlacitko(Titulek.BTN_ZPRISTUPNI_MAX.getNadpis());
        this.zpristupniMaxBtn.setDisable(true);

        nastavKomponentHaldy();
    }

    /**
     * Nastavuje vzhled a chování komponenty stromu představující sadu základích operací nad stromem
     */
    private void nastavKomponentHaldy() {
        this.setText(
                Titulek.KOMPONENT_HALDA.getNadpis());
        this.setContent(dejGridPane());
    }

    /**
     * Vrací mřížkový panel s umístěnými tlačítky
     *
     * @return Mřížkový panel {@link GridPane} s tlačítky a výchozím nastavením
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

// <editor-fold defaultstate="collapsed" desc="Action: vloz(Comparator<Obec> komp)">
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
                        ZpravaLogu.LOG_TVORENI_PRAZDNY_KLIC.getZprava());
                return;
            }
            new TvurceObce().vytvor(dialog)
                    .ifPresentOrElse(
                            novaObec -> {
                                seznamPanel.pridej(novaObec);
                                obnovTlacitkaProVlozeni();
                            },
                            () -> ErrorAlert.nahlasErrorLog(
                                    ZpravaLogu.LOG_TVORENI_SPATNA_POLE.getZprava()));
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
        seznamPanel.vymaz();
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
            provedObnoveniTlacitekSirkaHloubka();
        } else if (jeVybranaAkceProVraceni(zvolenaAkce)) {
            seznamPanel.schovejStrom();
            obnovTlacitkaProVrat();
            this.vypisCb.getSelectionModel().select(
                    Titulek.CB_VYPIS.getNadpis());
        }
    }

    private void provedObnoveniTlacitekSirkaHloubka() {
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
                Titulek.CB_VRAT.getNadpis(),
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
                Titulek.CB_SIRKA.getNadpis(),
                Titulek.CB_HLOUBKA.getNadpis());
    }

    /**
     * Zjišťuje, zda je zadaná položka určena pro prohlížení stromu
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true}, pokud je položka pro prohlížení, jinak {@code false}
     */
    private boolean jeVybranaAkceProProhlizeni(String polozka) {
        return Titulek.CB_SIRKA.getNadpis().equalsIgnoreCase(polozka)
                || Titulek.CB_HLOUBKA.getNadpis().equalsIgnoreCase(polozka);
    }

    /**
     * Vrací typ prohlížení na základě zvolené položky
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return Typ prohlížení na základě zvolené položky
     *
     * @see ETypProhl
     */
    private ETypProhl dejVybranyTyp(@NotNull String polozka) {
        return polozka.equalsIgnoreCase(Titulek.CB_SIRKA.getNadpis())
                ? ETypProhl.SIRKA : ETypProhl.HLOUBKA;
    }

    /**
     * Zjišťuje, zda je zadaná položka určena pro návrat v rámci stromu
     *
     * @param polozka Zvolená položka v {@link ChoiceBox}
     *
     * @return {@code true}, pokud je položka pro návrat, jinak {@code false}
     */
    private boolean jeVybranaAkceProVraceni(String polozka) {
        return Titulek.CB_VRAT.getNadpis().equalsIgnoreCase(polozka);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: jePrazdna()">
    /**
     * Zobrazí informační dialogové okénko s aktuálním počtem prvků v rámci seznamu
     */
    private void nastavUdalostPrazdnosti() {
        InfoAlert.nahlasInfoLog(
                String.valueOf(seznamPanel.dejMohutnost()));
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: zrus()">
    /**
     * Zruší všechny položky seznamu
     */
    private void nastavUdalostZruseni() {
        seznamPanel.vyprazdni();
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
