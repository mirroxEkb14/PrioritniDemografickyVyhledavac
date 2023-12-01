package cz.upce.fei.bdats.validatory;

/**
 * Třída implementuje sadu základních operací pro <i>validaci</i> vstupů typu {@link Integer}
 *
 * <p> Platné celé číslo je považované za takové, jež je rovno nebo větší než {@code 0}
 */
public final class IntegerValidator implements Validator<Integer> {

    /** Konstanta pro dolní kladnou mez platných celých čísel */
    private static final int DOLNI_KLADNA_MEZ = 0;

    @Override
    public boolean jeValidni(Integer vstup) {
        return vstup >= DOLNI_KLADNA_MEZ;
    }
}
