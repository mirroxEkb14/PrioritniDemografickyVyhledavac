package cz.upce.fei.bdats.agenda;

import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.AgendaKrajException;

import java.util.Comparator;
import java.util.Iterator;

public interface IAgendaKraj<E> {

    void vybuduj(E[] pole) throws AgendaKrajException;

    void reorganizuj(Comparator<E> komp) throws AgendaKrajException;

    void vloz(E prvek) throws AgendaKrajException;

    E odeberMax() throws AgendaKrajException;

    boolean jePrazdna();

    int mohutnost();

    void zrus();

    E zpristupniMax() throws AgendaKrajException;

    String vypis(ETypProhl typ) throws AgendaKrajException;

    Iterator<E> vytvorIterator(ETypProhl typ);

    void generuj(int pocet) throws AgendaKrajException;

    boolean nacti(String cesta);

    boolean uloz();
}
