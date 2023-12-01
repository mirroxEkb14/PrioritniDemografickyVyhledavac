package cz.upce.fei.bdats.gui.tvurce;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.gui.dialogy.DialogVlozeni;
import cz.upce.fei.bdats.gui.dialogy.DialogovyKomponent;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.ObecException;
import cz.upce.fei.bdats.vyjimky.PrazdnyRetezecException;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací pro <i>tvorbu</i> nových obcí
 *
 * <p> Implementuje rozhraní {@link Tvoritelny}
 */
public final class TvurceObce implements Tvoritelny<Obec> {

    @Override
    public Optional<Obec> vytvor(@NotNull DialogovyKomponent dialogVlozeni) {
        try {
            if (dialogVlozeni instanceof DialogVlozeni dialog) {
                final int cisloKraje = this.dejCeleCislo(dialog.getTfCislo().getText());
                final String nazevObce = dialog.getTfNazevObce().getText();
                final String nazevKraje = dialog.getTfNazevKraje().getText();
                final String pscObce = this.dejNeprazdnyRetezec(dialog.getTfPSC().getText());
                final int pocetMuzu = this.dejCeleCislo(dialog.getTfPocetMuzu().getText());
                final int pocetZen = this.dejCeleCislo(dialog.getTfPocetZen().getText());
                final int celkem = pocetMuzu + pocetZen;

                return Optional.of(new Obec(
                        cisloKraje,
                        nazevKraje,
                        nazevObce,
                        pscObce,
                        pocetMuzu,
                        pocetZen,
                        celkem));
            }
            return Optional.empty();
        } catch (CeleKladneCisloException | PrazdnyRetezecException | ObecException ex) {
            return Optional.empty();
        }
    }
}
