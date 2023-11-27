package strom;

import cz.upce.fei.bdats.halda.AbstrHeap;
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.vyjimky.zpravy.HeapZprava;
import org.junit.*;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * Testovací případy pro:
 * <ol>
 * <li> <b>test_01_</b>: Scénáře metody {@link AbstrHeap#vybuduj(Comparable[])}
 * <li> <b>test_02_</b>: Scénáře metody {@link AbstrHeap#reorganizuj(Comparator)}
 * <li> <b>test_03_</b>: Scénáře metody {@link AbstrHeap#vloz(Comparable)}
 * <li> <b>test_04_</b>: Scénáře metody {@link AbstrHeap#odeberMax()}
 * <li> <b>test_05_</b>: Scénáře metody {@link AbstrHeap#zpristupniMax()}
 * <li> <b>test_06_</b>: Scénáře metody {@link AbstrHeap#vypis(ETypProhl)}
 * </ol>
 *
 * @author amirov 11/26/2023
 */
public final class AbstrHeapTest {

// <editor-fold defaultstate="collapsed" desc="Nastavení">
    /**
     * Porovnání dvou proměnných typu {@code double} nebo {@code float} nelze operátorem rovnosti, protože
     * binární reprezentace na první pohled stejných hodnot nemusí stejná. Proto se hodnoty musí porovnat
     * výpočtem, zda jejich rozdíl je dostatečně malý
     */
    private static final double DELTA = Double.MIN_NORMAL;

    /**
     * Porovnávací kritéria
     */
    private static final Comparator<Integer> intKomparator = Comparator.naturalOrder();
    private static final Comparator<String> strKomparator = Comparator.naturalOrder();

    /**
     * Instance datové struktury s prioritou celých čísel {@link Integer} a textových řetězců {@link String}
     */
    private IAbstrHeap<Integer> intHalda;
    private IAbstrHeap<String> strHalda;

    public AbstrHeapTest() {}

    @BeforeClass
    public static void setUpClass() {}

    @AfterClass
    public static void tearDownClass() {}

    @Before
    public void setUp() {
        try {
            intHalda = new AbstrHeap<>(intKomparator);
            strHalda = new AbstrHeap<>(strKomparator);
        } catch (HeapException ignored) {}
    }

    @After
    public void tearDown() {
        intHalda = null;
        strHalda = null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="vybuduj(Comparable[])">
    @Test
    public void test_01_01_vybuduj() {
        try {
            final IAbstrHeap<Integer> heap =
        } catch (HeapException ex) {
            fail();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="reorganizuj(Comparator)">
    @Test
    public void test_02_01_reorganizuj() {

    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="vloz(Comparable)">
    /**
     * Vloží prvek do prazdné haldy
     */
    @Test
    public void test_03_01_vloz() {
        try {
            intHalda.vloz(42);

            final int expected1 = 1;
            final int result1 = intHalda.mohutnost();
            assertEquals(expected1, result1);

            final int expected2 = 42;
            final int result2 = intHalda.odeberMax();
            assertEquals(expected2, result2);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Vloží cice prvků
     */
    @Test
    public void test_03_02_vloz() {
        try {
            intHalda.vloz(30);
            intHalda.vloz(20);
            intHalda.vloz(40);
            intHalda.vloz(10);

            final int expectedMohutnost = 4;
            final int resultMohutnost = intHalda.mohutnost();
            assertEquals(expectedMohutnost, resultMohutnost);

            final int expected1 = 40;
            final int result1 = intHalda.odeberMax();
            assertEquals(expected1, result1);

            final int expected2 = 30;
            final int result2 = intHalda.odeberMax();
            assertEquals(expected2, result2);

            final int expected3 = 20;
            final int result3 = intHalda.odeberMax();
            assertEquals(expected3, result3);

            final int expected4 = 10;
            final int result4 = intHalda.odeberMax();
            assertEquals(expected4, result4);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Vloží s expanzi kapacity
     */
    @Test
    public void test_03_03_vloz() {
        try {
            final IAbstrHeap<Integer> heap = new AbstrHeap<>(2, Integer::compareTo);
            heap.vloz(30);
            heap.vloz(20);
            heap.vloz(40); // kapacita = 2 * 2 = 4, mohutnost = 2 + 1 = 3

            final int expectedMohutnost = 3;
            final int resultMohutnost = heap.mohutnost();
            assertEquals(expectedMohutnost, resultMohutnost);

            final int expected1 = 40;
            final int result1 = heap.odeberMax();
            assertEquals(expected1, result1);

            final int expected2 = 30;
            final int result2 = heap.odeberMax();
            assertEquals(expected2, result2);

            final int expected3 = 20;
            final int result3 = heap.odeberMax();
            assertEquals(expected3, result3);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Vloží do prázdné haldy s výjimkou
     */
    @Test
    public void test_03_04_vloz() {
        assertThrows(HeapZprava.PRAZDNY_PRVEK.zprava(),
                HeapException.class,
                () -> strHalda.vloz(null));
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="odeberMax()">
    @Test
    public void test_04_01_odeberMax() {
        try {
            intHalda.vloz(5);
            intHalda.vloz(8);
            intHalda.vloz(3);

            final int expected1 = 8;
            final int result1 = intHalda.odeberMax();
            assertEquals(expected1, result1);

            final int expected2 = 5;
            final int result2 = intHalda.odeberMax();
            assertEquals(expected2, result2);

            final int expected3 = 3;
            final int result3 = intHalda.odeberMax();
            assertEquals(expected3, result3);
        } catch (HeapException ex) {
            fail();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="zpristupniMax()">
    @Test
    public void test_05_01_zpristupniMax() {
        try {
            intHalda.vloz(5);
            intHalda.vloz(8);
            intHalda.vloz(3);

            final int expected1 = 8;
            final int result1 = intHalda.zpristupniMax();
            assertEquals(expected1, result1);

            intHalda.odeberMax();
            final int expected2 = 5;
            final int result2 = intHalda.zpristupniMax();
            assertEquals(expected2, result2);

            intHalda.odeberMax();
            final int expected3 = 3;
            final int result3 = intHalda.zpristupniMax();
            assertEquals(expected3, result3);
        } catch (HeapException ex) {
            fail();
        }
    }

    @Test
    public void test_05_02_zpristupniMax() {
        assertThrows(HeapZprava.PRAZDNE_POLE.zprava(),
                HeapException.class,
                intHalda::zpristupniMax);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="vypis(ETypProhl)">
    @Test
    public void test_06_01_vypis() {

    }
// </editor-fold>
}
