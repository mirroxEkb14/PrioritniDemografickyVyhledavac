package upce.cz.fei.bdats.vyjimky;

import upce.cz.fei.bdats.model.Obec;

/**
 * Třída je vlastní výjimkou pro {@link Obec} na její specifické vlastnosti
 *
 * <p> Tato výjimka je typu {@link RuntimeException}, které sice nejsou kritické z hlediska samotné možnosti
 * pokračování aplikace (na rozdíl od {@link Error}), protože je to typ výjimek takový, že nemusí být zachycen
 * explicitně (není checked exception). Jde tedy o běhovou výjimku, která značí chyby ve zdrojovém kódu
 * způsobené programátorskými nedostatky
 */
public final class ObecException extends Exception {

    public ObecException(String zprava) { super(zprava); }
}
