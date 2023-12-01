package cz.upce.fei.bdats.sekvence.fifo;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.sekvence.seznam.AbstrDoubleList;
import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.DoubleListException;
import cz.upce.fei.bdats.vyjimky.FifoException;
import cz.upce.fei.bdats.vyjimky.zpravy.FifoZprava;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací abstraktní datové strultury <i>(ADS)</i> <b>fronta</b> založené
 * na principu FIFO a postaveným nad ADS <b>obousměrně necyklický zřetězený lineární seznam</b>
 *
 * <p> Třída reprezentuje dynamickou datovou strukturu implementující typ ADS dynamický lineární seznam <i>(eng.
 * Doubly Linked List)</i>
 *
 * <p> Implementuje rozhraní {@link IAbstrFifo}
 *
 * @param <T> Budoucí data typu {@code T}
 */
public final class AbstrFifo<T> implements IAbstrFifo<T> {

    private final IAbstrDoubleList<T> fronta;

    /**
     * Konstruktor
     * <p>
     * Vytvoří novou instanci fronty
     */
    public AbstrFifo() { fronta = new AbstrDoubleList<>(); }

    @Override
    public void zrus() { fronta.zrus(); }

    @Override
    public boolean jePrazdna() { return fronta.jePrazdny(); }

    @Override
    public int mohutnost() { return fronta.mohutnost(); }

    @Override
    public void vlozNaKonec(T data) throws FifoException {
        try {
            fronta.vlozPosledni(data);
        } catch (DoubleListException ex) {
            throw new FifoException(
                    FifoZprava.NULL_VSTUPNI_DATA.zprava());
        }
    }

    @Override
    public T odeberZeZacatku() throws FifoException {
        try {
            return fronta.odeberPrvni();
        } catch (DoubleListException ex) {
            throw new FifoException(
                    FifoZprava.PRAZDNA_FRONTA.zprava());
        }
    }

    @Override
    public T zpristupniZacatek() throws FifoException {
        try {
            return fronta.zpristupniPrvni();
        } catch (DoubleListException ex) {
            throw new FifoException(
                    FifoZprava.PRAZDNA_FRONTA.zprava());
        }
    }

    @Override
    public T zpristupniKonec() throws FifoException {
        try {
            return fronta.zpristupniPosledni();
        } catch (DoubleListException ex) {
            throw new FifoException(
                    FifoZprava.PRAZDNA_FRONTA.zprava());
        }
    }
}

