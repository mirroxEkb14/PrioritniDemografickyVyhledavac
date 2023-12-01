package cz.upce.fei.bdats.gui.tvurce;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.gui.dialogy.DialogovyKomponent;
import cz.upce.fei.bdats.validatory.IntegerValidator;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.PrazdnyRetezecException;

import java.util.Optional;
import java.util.function.Predicate;
// </editor-fold>

/**
 * Rozhraní definuje sadu základnách operací pro <i>tvorbu</i> nových objektů
 *
 * @param <T> Typ objektu, jenž má být vytvořen
 */
public interface Tvoritelny<T> {

    /**
     * Validátor ověří, zda vstupní řetězec je prázdný nebo není
     */
    Predicate<String> validatorPrazdnychRetezcu = String::isEmpty;

    IntegerValidator validator = new IntegerValidator();

    /**
     * Vytvoří objekt typu {@link T}
     *
     * @param dialog Instance dialogu, z něhož se budou volat gettery pro zjišťování dat zadaných uživatelem
     *
     * @return Instance vytvořeného objektu, jinak prázdná hodnota {@link Optional}, pokud není možné objekt vytvořit
     * kvůli špatně zadaným údajů uživatelem
     */
    Optional<T> vytvor(DialogovyKomponent dialog);

    /**
     * Převede zadaný řetězec na celé číslo pomocí validátoru {@link Tvoritelny#validator} pro ověření,
     * zda je vstupní řetězec platným celým kladným číslem
     *
     * @param retezec Textový řetězec, jenž má být převeden na celé číslo
     *
     * @return Celé číslo typu {@link Integer}
     *
     * @throws CeleKladneCisloException Když zadaný řetězec není celým číslem větším než nula
     */
    default int dejCeleCislo(String retezec) throws CeleKladneCisloException {
        try {
            final int cislo = Integer.parseInt(retezec);
            if (validator.jeValidni(cislo))
                return cislo;
            else
                throw new CeleKladneCisloException();
        } catch (NumberFormatException ex) {
            throw new CeleKladneCisloException();
        }
    }

    /**
     * Ověří hodnotu vstupního řetězce a vrátí ho v případě, že není prázdný
     *
     * @param retezec Textový řetězec, jenž má být ověřen na prázdnost a být vracen
     *
     * @return Hodnota vstupního textového řetězce
     *
     * @throws PrazdnyRetezecException Když je zadaný řetězec prázdný
     *
     */
    default String dejNeprazdnyRetezec(String retezec) throws PrazdnyRetezecException {
        if (!validatorPrazdnychRetezcu.test(retezec))
            return retezec;
        throw new PrazdnyRetezecException();
    }
}
