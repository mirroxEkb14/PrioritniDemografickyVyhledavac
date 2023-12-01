package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.fifo.IAbstrFifo;
import cz.upce.fei.bdats.vyjimky.zpravy.FifoZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see IAbstrFifo
 * @see FifoZprava
 */
public final class FifoException extends RuntimeException {

    public FifoException(String zprava) { super(zprava); }
}
