package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.vyjimky.zpravy.HeapZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link Exception}
 *
 * <p> Vystaví se, když:
 * <ul>
 * <li> Jsou vstupní data {@code null}
 * <li> Je prioritní fronta prázdný
 * </ul>
 *
 * @see IAbstrHeap
 * @see HeapZprava
 */
public final class HeapException extends Exception {

    public HeapException(String zprava) { super(zprava); }
}
