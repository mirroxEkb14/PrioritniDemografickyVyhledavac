package cz.upce.fei.bdats.validatory;

/**
 * Třída implementuje {@link Validator} a poskytuje validaci pro celá čísla
 *
 * <p> Platné celé číslo je považované za takové, které je rovno nebo větší než {@code 0}
 */
public final class IntegerValidator implements Validator<Integer> {

    /** Konstanta pro dolní kladnou mez platných celých čísel */
    private static final int DOLNI_KLADNA_MEZ = 0;

    @Override
    public boolean jeValidni(Integer vstup) {
        return vstup >= DOLNI_KLADNA_MEZ;
    }
}
