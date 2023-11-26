package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.lifo.IAbstrLifo;
import cz.upce.fei.bdats.vyjimky.zpravy.LifoZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link Exception}
 *
 * <p> Vystaví se, když:
 * <ul>
 * <li> Jsou vstupní data {@code null}
 * <li> Je zásobník prázdný
 * </ul>
 *
 * @see IAbstrLifo
 * @see LifoZprava
 */
public final class LifoException extends Exception {

    public LifoException(String zprava) { super(zprava); }
}
