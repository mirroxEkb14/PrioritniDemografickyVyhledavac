package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.gui.tvurce.Tvoritelny;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see Tvoritelny
 */
public final class PrazdnyRetezecException extends RuntimeException {

    public PrazdnyRetezecException() { super("Špatně zadaný textový řetězec"); }
}
