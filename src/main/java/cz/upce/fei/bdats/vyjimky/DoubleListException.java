package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.zpravy.DoubleListZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see IAbstrDoubleList
 * @see DoubleListZprava
 */
public final class DoubleListException extends RuntimeException {

    public DoubleListException(String zprava) { super(zprava); }
}
