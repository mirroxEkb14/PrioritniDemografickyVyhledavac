package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.zpravy.DoubleListZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link Exception}
 *
 * <p> Vystaví se, když:
 * <ul>
 * <li> Jsou vstupní data {@code null}
 * <li> Je seznam prázdný
 * <li> Není nastaven aktuální ukazatel
 * <li> Aktuální ukazatel je nastaven na první/poslední prvek seznamu
 * </ul>
 *
 * @see IAbstrDoubleList
 * @see DoubleListZprava
 */
public final class DoubleListException extends Exception {

    public DoubleListException(String zprava) { super(zprava); }
}
