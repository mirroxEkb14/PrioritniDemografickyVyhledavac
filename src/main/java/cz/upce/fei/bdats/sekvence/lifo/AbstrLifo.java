package cz.upce.fei.bdats.sekvence.lifo;

import cz.upce.fei.bdats.sekvence.seznam.AbstrDoubleList;
import cz.upce.fei.bdats.sekvence.seznam.IAbstrDoubleList;
import cz.upce.fei.bdats.vyjimky.DoubleListException;
import cz.upce.fei.bdats.vyjimky.LifoException;
import cz.upce.fei.bdats.vyjimky.zpravy.LifoZprava;

/**
 * Třída implementuje sadu základních operací abstraktní datové strultury <i>(ADS)</i> <b>zásobník</b> založené
 * na principu LIFO a postaveným nad ADS <b>obousměrně necyklický zřetězený lineární seznam</b>
 *
 * <p> Třída reprezentuje dynamickou datovou strukturu implementující typ ADS dynamický lineární seznam <i>(eng.
 * Doubly Linked List)</i>
 *
 * <p> Implementuje rozhraní {@link IAbstrLifo}
 *
 * @param <T> Budoucí data typu {@code T}
 */
public final class AbstrLifo<T> implements IAbstrLifo<T> {

    private final IAbstrDoubleList<T> zasobnik;

    /**
     * Konstruktor
     * <p>
     * Vytvoří novou instanci zásobníku
     */
    public AbstrLifo() { zasobnik = new AbstrDoubleList<>(); }

    @Override
    public void zrus() { zasobnik.zrus(); }

    @Override
    public boolean jePrazdny() { return zasobnik.jePrazdny(); }

    @Override
    public int mohutnost() { return zasobnik.mohutnost(); }

    @Override
    public void vlozNaZacatek(T data) throws LifoException {
        try {
            zasobnik.vlozPrvni(data);
        } catch (DoubleListException ex) {
            throw new LifoException(
                    LifoZprava.NULL_VSTUPNI_DATA.zprava());
        }
    }

    @Override
    public T odeberZeZacatku() throws LifoException {
        try {
            return zasobnik.odeberPrvni();
        } catch (DoubleListException ex) {
            throw new LifoException(
                    LifoZprava.PRAZDNY_ZASOBNIK.zprava());
        }
    }

    @Override
    public T zpristupniZacatek() throws LifoException {
        try {
            return zasobnik.zpristupniPrvni();
        } catch (DoubleListException ex) {
            throw new LifoException(
                    LifoZprava.PRAZDNY_ZASOBNIK.zprava());
        }
    }
}

