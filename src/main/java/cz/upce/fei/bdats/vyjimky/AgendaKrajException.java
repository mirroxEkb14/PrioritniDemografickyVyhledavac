package cz.upce.fei.bdats.vyjimky;

import cz.upce.fei.bdats.agenda.IAgendaKraj;
import cz.upce.fei.bdats.vyjimky.zpravy.AgendaKrajZprava;

/**
 * Třída je vlastní výjimkou rozšiřující třídu {@link RuntimeException}
 *
 * @see IAgendaKraj
 * @see AgendaKrajZprava
 */
public final class AgendaKrajException extends RuntimeException {

    public AgendaKrajException(String message) {
        super(message);
    }
}
