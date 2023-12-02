package cz.upce.fei.bdats.gui.tvurce;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.generator.Generator;
import cz.upce.fei.bdats.generator.ObecGenerator;
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

    /**
     * <b>Poznámka</b>: Nezpracovává výjimku <b>catch (NumberFormatException ignored) {}</b>, protože pole čísel
     * musí být zkontrolováno před voláním této metody
     */
    @Override
    public Obec @NotNull [] vytvorPodleCisla(String @NotNull [] celkovePocty) {
        final Generator<Obec> generator = new ObecGenerator();
        final Obec[] obce = new Obec[celkovePocty.length];
        try {
            for (int i = 0; i < celkovePocty.length; i++) {
                final int pocet = Integer.parseInt(celkovePocty[i]);
                obce[i] = generator.generujPodlePoctu(pocet);
            }
        } catch (NumberFormatException ignored) {}
        return obce;
    }

    @Override
    public Obec @NotNull [] vytvorPodleTextu(String @NotNull [] nazvyObci) {
        final Generator<Obec> generator = new ObecGenerator();
        final Obec[] obce = new Obec[nazvyObci.length];
        for (int i = 0; i < nazvyObci.length; i++) {
            final String nazev = nazvyObci[i];
            obce[i] = generator.generujPodleNazvu(nazev);
        }
        return obce;
    }
}
