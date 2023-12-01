package cz.upce.fei.bdats.validatory;

/**
 * Třída implementuje sadu základních operací pro <i>validaci</i> vstupů typu {@link String}
 *
 * <p> Platný textový řetězec je považován za takový, jenž není {@code null} a není prázdný po odstranění
 * případných bílých znaků - {@link String#trim()}
 */
public final class TextValidator implements Validator<String> {

    @Override
    public boolean jeValidni(String vstup) {
        return vstup != null && !vstup.trim().isEmpty();
    }
}
