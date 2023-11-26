package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.fifo.IAbstrFifo;
import cz.upce.fei.bdats.vyjimky.zpravy.FifoZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link Exception}
 *
 * <p> Vystaví se, když:
 * <ul>
 * <li> Jsou vstupní data {@code null}
 * <li> Je fronta prázdný
 * </ul>
 *
 * @see IAbstrFifo
 * @see FifoZprava
 */
public final class FifoException extends Exception {

    public FifoException(String zprava) { super(zprava); }
}
