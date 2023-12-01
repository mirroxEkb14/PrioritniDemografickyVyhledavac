package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.strom.IAbstrTable;
import cz.upce.fei.bdats.vyjimky.zpravy.StromZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * <p> Vystaví se, když:
 * <ul>
 * <li> Vstupní klíč není platný
 * <li> Vstupní klíč je {@code null}
 * <li> Vstupní hodnota je {@code null}
 * <li> Je strom prázdný
 * <li> Je pomocná struktura zásobník prázdná
 * <li> Je pomocná struktura fronta prázdná
 * </ul>
 *
 * @see IAbstrTable
 * @see StromZprava
 */
public final class StromException extends RuntimeException {

    public StromException(String zprava) { super(zprava); }
}
