package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.gui.tvurce.Tvoritelny;
import cz.upce.fei.bdats.generator.Generator;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see Tvoritelny
 * @see Generator
 */
public final class CeleKladneCisloException extends RuntimeException {

    public CeleKladneCisloException() { super("Špatně zadané celé číslo"); }
}
