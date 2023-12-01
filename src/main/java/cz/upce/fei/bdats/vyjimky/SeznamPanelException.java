package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.vyjimky.zpravy.SeznamPanelZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see SeznamPanelZprava
 */
public final class SeznamPanelException extends RuntimeException {

    public SeznamPanelException(String message) { super(message); }
}
