package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.vyjimky.zpravy.ObecZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * <p> <b>Poznámka</b>: Tato výjimka je typu {@link RuntimeException}, které sice nejsou kritické z hlediska samotné možnosti
 * pokračování aplikace (na rozdíl od {@link Error}), protože je to typ výjimek takový, že nemusí být zachycen
 * explicitně (není checked exception). Jde tedy o běhovou výjimku, která značí chyby ve zdrojovém kódu
 * způsobené programátorskými nedostatky
 *
 * @see Obec
 * @see ObecZprava
 */
public final class ObecException extends RuntimeException {

    public ObecException(String zprava) { super(zprava); }
}
