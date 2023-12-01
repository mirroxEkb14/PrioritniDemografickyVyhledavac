package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.sekvence.lifo.IAbstrLifo;
import cz.upce.fei.bdats.vyjimky.zpravy.LifoZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see IAbstrLifo
 * @see LifoZprava
 */
public final class LifoException extends RuntimeException {

    public LifoException(String zprava) { super(zprava); }
}
