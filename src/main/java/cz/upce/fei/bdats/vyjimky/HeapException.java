package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.vyjimky.zpravy.HeapZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see IAbstrHeap
 * @see HeapZprava
 */
public final class HeapException extends RuntimeException {

    public HeapException(String zprava) { super(zprava); }
}
