package cz.upce.fei.bdats.agenda;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;
import cz.upce.fei.bdats.vyjimky.CeleKladneCisloException;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.generator.Generator;
import cz.upce.fei.bdats.perzistence.IPerzistence;

import java.util.Comparator;
import java.util.Iterator;
// </editor-fold>

/**
 * Rozhraní definuje sadu základních operací pro <i>ověření funkčnosti</i> implementováných ADS umožňující
 * <i>správu</i> objektu, jehož typ nahradí generický
 *
 * @param <E> Generický typ, jenž bude nahrazen typem prvků prioritní fronty
 */
public interface IAgendaKraj<E> {

    /**
     * Vybuduje prioritní frontu (uspořádanou) podle požadovaného pole
     *
     * @param pole Pole prvků
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#vybuduj(E[])
     */
    void vybuduj(E[] pole) throws AgendaKrajException;

    /**
     * Reorganizuje prioritní fronty podle nového kritéria
     *
     * @param komp Nový kritérium porovnávání prvků v haldě
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#reorganizuj(Comparator)
     */
    void reorganizuj(Comparator<E> komp) throws AgendaKrajException;

    /**
     * Zruší celou prioritní frontu
     *
     * @see IAbstrHeap#zrus()
     */
    void zrus();

    /**
     * Zkontroluje, zda je prioritní fronta prázdná
     *
     * @return {@code true} pokud je halda prázdná, jinak {@code false}
     *
     * @see IAbstrHeap#jePrazdna()
     */
    boolean jePrazdna();

    /**
     * Vrátí aktuální počet prvků v prioritní frontě
     *
     * @return Číslo počtu prvků
     *
     * @see IAbstrHeap#mohutnost()
     */
    int mohutnost();

    /**
     * Vrátí iterátor požadovaného typu
     *
     * @param typ Typ prohlížení: buďto {@link ETypProhl#SIRKA}, anebo {@link ETypProhl#HLOUBKA}
     *
     * @return Instance iterátoru určitého typu
     *
     * @see IAbstrHeap#vytvorIterator(ETypProhl)
     */
    Iterator<E> vytvorIterator(ETypProhl typ);

    /**
     * Vloží nový prvek do prioritní fronty
     *
     * @param prvek Prvek, jenž bude vložen do haldy
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#vloz(E)
     */
    void vloz(E prvek) throws AgendaKrajException;

    /**
     * Odeber prvek s největší prioritou z haldy
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#odeberMax()
     */
    E odeberMax() throws AgendaKrajException;

    /**
     * Vrátí prvek s největší prioritou v haldě
     *
     * @return Prvek z hlavičky haldy
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#zpristupniMax()
     */
    E zpristupniMax() throws AgendaKrajException;

    /**
     * Vygeneruje nové obce
     *
     * @param pocet Počet prvků pro vytváření
     *
     * @throws CeleKladneCisloException Když není hodnota parametru rovna nebo větší než {@code 0}
     *
     * @see Generator#generuj(IAbstrHeap, int)
     */
    void generuj(int pocet) throws CeleKladneCisloException;

    /**
     * Vrátí posloupnost prvků v prioritní frontě podle požadovaného typu prohlížení
     *
     * @param typ Typ prohlížení: buďto {@link ETypProhl#SIRKA}, anebo {@link ETypProhl#HLOUBKA}
     *
     * @return Textový řetězec s posloupnosti prvků haldy
     *
     * @throws AgendaKrajException Když se vystaví výjimka {@link HeapException}
     *
     * @see IAbstrHeap#vypis(ETypProhl)
     */
    String vypis(ETypProhl typ) throws AgendaKrajException;

    /**
     * Načte obce ze souboru
     *
     * @param cesta Cesta k souboru s obcemi
     *
     * @return {@code true} pokud načtení proběhlo bez i/o výjimek, jinak {@code false}
     *
     * @see IPerzistence#nactiCsv(IAbstrHeap, String)
     */
    boolean nacti(String cesta);

    /**
     * Uloží aktuální stav prioritní fronty do souboru
     *
     * @return {@code true} pokud uložení proběhlo bez i/o výjimek, jinak {@code false}
     *
     @see IPerzistence#ulozCsv(IAbstrHeap)
     */
    boolean uloz();
}
