package cz.upce.fei.bdats.gui.komponenty;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.Titulek;
import cz.upce.fei.bdats.gui.alerty.ErrorAlert;
import cz.upce.fei.bdats.gui.alerty.InfoAlert;
import cz.upce.fei.bdats.gui.dialogy.DialogGeneratoru;
import cz.upce.fei.bdats.gui.kontejnery.MrizkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.TitulkovyPanel;
import cz.upce.fei.bdats.gui.kontejnery.Tlacitko;
import cz.upce.fei.bdats.gui.koreny.ISeznamPanel;
import cz.upce.fei.bdats.gui.koreny.SeznamPanel;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.perzistence.IPerzistence;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.zpravy.LogZprava;
import cz.upce.fei.bdats.generator.Generator;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;
// </editor-fold>

/**
 * Třída implementuje sadu operací pro <i>ovládání</i> tlačítek, jež pracují s vedlejšími operacemi prioritní
 * fronty
 *
 * <p> Třída je vzorem <b>Singleton</b>
 *
 * <p> Rozšiřuje třídu {@link TitulkovyPanel}
 *
 * @see Generator
 * @see IPerzistence
 */
public class KomponentPrikazy extends TitulkovyPanel {

// <editor-fold defaultstate="collapsed" desc="Atributy/Instanční proměnné">
    private final Button generujBtn, ulozBtn;
    private final ChoiceBox<String> nactiCb = new ChoiceBox<>();

    private final ISeznamPanel<Obec> seznamPanel = SeznamPanel.getInstance();

    /**
     * Atribut reprezentuje lambdový výraz, jenž předefinuje výběrové pole {@link ChoiceBox}
     */
    private final BiConsumer<String, String> tvurceCbNacteni = (t, u) -> {
        this.nactiCb.getItems().clear();
        this.nactiCb.getItems().addAll(
                Titulek.CB_NACTI.nadpis(), t, u);
        if (IPerzistence.jeSoubor(IPerzistence.CESTA_ULOZISTE))
            this.nactiCb.getItems().add(
                    Titulek.CB_ULOZISTE.nadpis());
        this.nactiCb.setPrefWidth(PREFEROVANA_SIRKA_POLE);
        this.nactiCb.getSelectionModel().select(
                Titulek.CB_NACTI.nadpis());
        this.nactiCb.setOnAction(actionEvent -> nastavUdalostNacteni());
    };
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Instance a Tovární Metoda">
    private static KomponentPrikazy instance;

    public static KomponentPrikazy getInstance() {
        if (instance == null)
            instance = new KomponentPrikazy();
        return instance;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Nastavení třídy">
    public KomponentPrikazy() {
        this.generujBtn = new Tlacitko(Titulek.BTN_GENERUJ.nadpis());
        this.generujBtn.setOnAction(actionEvent -> nastavUdalostGeneratoru());

        tvurceCbNacteni.accept(
                Titulek.CB_VZOR.nadpis(),
                Titulek.CB_KRAJE.nadpis());

        this.ulozBtn = new Tlacitko(Titulek.BTN_ULOZ.nadpis());
        this.ulozBtn.setOnAction(actionEvent -> nastavUdalostUlozeni());
        this.ulozBtn.setPrefWidth(Math.pow(Tlacitko.getPrefSirka(), 2));
        this.ulozBtn.setDisable(true);

        nastavKomponentPrikazy();
    }

    /**
     * Provede výchozí nastavení pro tento grafický komponent
     */
    private void nastavKomponentPrikazy() {
        this.setText(Titulek.KOMPONENT_PRIKAZY.nadpis());
        this.setContent(dejGridPane());
    }

    /**
     * Nastaví a vratí nový {@link GridPane}
     *
     * @return Instance na nově vytvořený mřížkový panel {@link MrizkovyPanel}
     */
    private @NotNull GridPane dejGridPane() {
        final GridPane gridPane = new MrizkovyPanel();
        gridPane.add(generujBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(nactiCb, MrizkovyPanel.SLOUPCOVY_INDEX_DRUHY, MrizkovyPanel.RADKOVY_INDEX_PRVNI);
        gridPane.add(ulozBtn, MrizkovyPanel.SLOUPCOVY_INDEX_PRVNI, MrizkovyPanel.RADKOVY_INDEX_DRUHY);
        GridPane.setColumnSpan(ulozBtn, MrizkovyPanel.ROZPETI_SLOUPCU);
        return gridPane;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: generuj()/nacti()">
    private void nastavUdalostGeneratoru() {
        final DialogGeneratoru dialog = new DialogGeneratoru();
        final Optional<ButtonType> odpoved = dialog.showAndWait();
        if (odpoved.isPresent() && dialog.jeTlacitkoOk(odpoved.get())) {
            try {
                final int pocet = Integer.parseInt(dialog.getTfNazevObce().getText());
                seznamPanel.obnovSeznam(pocet);
                obnovTlacitkaGeneratorNacteni();
            } catch (NumberFormatException | CeleKladneCisloException ex) {
                ErrorAlert.nahlasErrorLog(
                        LogZprava.CHYBA_GENERATORU_SPATNY_POCET.getZprava());
            }
        }
    }

    private void nastavUdalostNacteni() {
        final String zvolenaPolozka = nactiCb.getSelectionModel().getSelectedItem();
        if (Titulek.CB_VZOR.nadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_VZOR))
                ErrorAlert.nahlasErrorLog(
                        LogZprava.CHYBA_NACTENI_VZORU.getZprava());
            else
                InfoAlert.nahlasInfoLog(
                        LogZprava.INFO_NACTENI_VZORU.getZprava());
        } else if (Titulek.CB_KRAJE.nadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_KRAJE))
                ErrorAlert.nahlasErrorLog(
                        LogZprava.CHYBA_NACTENI_KRAJE.getZprava());
            else
                InfoAlert.nahlasInfoLog(
                        LogZprava.INFO_NACTENI_KRAJE.getZprava());
        } else if (Titulek.CB_ULOZISTE.nadpis().equalsIgnoreCase(zvolenaPolozka)) {
            if (!seznamPanel.nacti(IPerzistence.CESTA_ULOZISTE))
                ErrorAlert.nahlasErrorLog(
                        LogZprava.CHYBA_NACTENI_ULOZISTE.getZprava());
            else
                InfoAlert.nahlasInfoLog(
                        LogZprava.INFO_NACTENI_ULOZISTE.getZprava());
        }
        this.nactiCb.getSelectionModel().select(Titulek.CB_NACTI.nadpis());
        obnovTlacitkaGeneratorNacteni();
    }

    private void obnovTlacitkaGeneratorNacteni() {
        if(KomponentHalda.getInstance().jeVypnutoBtnReprganizuj())
            KomponentHalda.getInstance().zapniBtnReorganizuj();
        if (KomponentHalda.getInstance().jeVypnutoBtnOdeberMax())
            KomponentHalda.getInstance().zapniBtnOdeberMax();
        if (KomponentHalda.getInstance().jeVypnutoBtnPrazdnost())
            KomponentHalda.getInstance().zapniBtnPrazdnost();
        if (KomponentHalda.getInstance().jeVypnutoBtnZrus())
            KomponentHalda.getInstance().zapniBtnZrus();
        if (KomponentHalda.getInstance().jeVypnutoBtnVypis())
            KomponentHalda.getInstance().zapniBtnVypis();
        if (KomponentHalda.getInstance().jeVypnutoBtnZpristupniMax())
            KomponentHalda.getInstance().zapniBtnZpristupniMax();
        if (this.jeVypnutoBtnUloz())
            this.zapniBtnUloz();
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Action: uloz()">
    private void nastavUdalostUlozeni() {
        if (seznamPanel.uloz()) {
            InfoAlert.nahlasInfoLog(
                    LogZprava.INFO_ULOZENI.getZprava());
            tvurceCbNacteni.accept(
                    Titulek.CB_VZOR.nadpis(),
                    Titulek.CB_KRAJE.nadpis());
            return;
        }
        ErrorAlert.nahlasErrorLog(
                LogZprava.CHYBA_ULOZENI.getZprava());
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné Metody: Kontrola Stavu Tlačítek">
    public boolean jeVypnutoBtnGeneruj() { return generujBtn.isDisabled(); }
    public boolean jeVypnutoBtnNacti() { return nactiCb.isDisabled(); }
    public boolean jeVypnutoBtnUloz() { return ulozBtn.isDisabled(); }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Veřejné Metody: Přepínače">
    public void zapniBtnGeneruj() { generujBtn.setDisable(false); }
    public void vypniBtnGeneruj() { generujBtn.setDisable(true); }

    public void zapniBtnNacti() { nactiCb.setDisable(false); }
    public void vypniBtnNacti() { nactiCb.setDisable(true); }

    public void zapniBtnUloz() { ulozBtn.setDisable(false); }
    public void vypniBtnUloz() { ulozBtn.setDisable(true); }
// </editor-fold>
}
