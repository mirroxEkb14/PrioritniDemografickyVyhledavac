package cz.upce.fei.bdats.validatory;

/**
 * Třída implementuje {@link Validator} a poskytuje validaci pro textové řetězce
 *
 * <p> Platný textový řetězec je považován za takový, který není {@code null} a není prázdný po odstranění
 * případných bílých znaků - {@link String#trim()}
 */
public final class TextValidator implements Validator<String> {

    @Override
    public boolean jeValidni(String vstup) {
        return vstup != null && !vstup.trim().isEmpty();
    }
}
