package halda;

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
 * <li> <b>test_01_</b>: Scénáře metody {@link AbstrHeap#vybuduj(Object[])}
 * <li> <b>test_02_</b>: Scénáře metody {@link AbstrHeap#reorganizuj(Comparator)}
 * <li> <b>test_03_</b>: Scénáře metody {@link AbstrHeap#vloz(Object)}
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
            intHalda = new AbstrHeap<>(Integer.class, intKomparator);
            strHalda = new AbstrHeap<>(String.class, strKomparator);
        } catch (HeapException ignored) {}
    }

    @After
    public void tearDown() {
        intHalda = null;
        strHalda = null;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="vybuduj(Comparable[])">
    /**
     * Vybuduje prázdnou haldu a pak ji ověří na prázdnost
     */
    @Test(expected = HeapException.class)
    public void test_01_01_vybuduj() throws HeapException {
        Integer[] pole = new Integer[0];
        intHalda.vybuduj(pole);
        fail();
    }

    /**
     * Vybuduje haldu z neutříděného pole a ověří, zda po vytvoření platí haldové uspořádání
     *
     * <p> Motivační obrázky:
     * <ul>
     * <li> Neupořádaná halda: [5, 2, 9, 1, 7]
     * <li> Uspořádaná halda: [9, 7, 5, 1, 2]
     *          9
     *        /   \
     *       7     5
     *     /  \
     *    1    2
     * <li> Potomci {@code 9}:
     *      <ul>
     *      <li> Levý syn = 7, protože 2 * 0 + 1 = 1 (tj. index)
     *      <li> Pravý syn = 5, protože 2 * 0 + 2 = 2 (tj. index)
     *      </ul>
     * <li> Potomci {@code 7}:
     *      <ul>
     *      <li> Levý syn = 1, protože 2 * 1 + 1 = 3 (tj. index)
     *      <li> Pravý syn = 2, protože 2 * 1 + 2 = 4 (tj. index)
     *      </ul>
     * </ul>
     */
    @Test
    public void test_01_02_vybuduj() {
        try {
            Integer[] pole = {5, 2, 9, 1, 7};
            intHalda.vybuduj(pole);

            final int expected1 = 5;
            final int result1 = intHalda.mohutnost();
            assertEquals(expected1, result1);

            final int expected2 = 9;
            final int result2 = intHalda.zpristupniMax();
            assertEquals(expected2, result2);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Vybuduje haldu z již utříděného pole a ověří, zda po vytvoření platí již existující haldové uspořádání
     */
    @Test
    public void test_01_03_vybuduj() {
        try {
            Integer[] pole = {9, 7, 5, 3, 1};
            intHalda.vybuduj(pole);

            final int expected1 = 5;
            final int result1 = intHalda.mohutnost();
            assertEquals(expected1, result1);

            final int expected2 = 9;
            final int result2 = intHalda.zpristupniMax();
            assertEquals(expected2, result2);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Vybuduje haldu z částečně utříděného pole a ověří, zda po vytvoření platí haldové uspořádání
     *
     * <p> Motivační obrázky:
     * <ul>
     * <li> Neupořádaná halda: [5, 2, 9, 1, 7, 4]
     * <li> Uspořádaná halda: [9, 7, 5, 1, 2, 4]
     *          9
     *        /   \
     *      7      5
     *    /  \    /
     *   1    2  4
     * <li> Potomci {@code 9}:
     *      <ul>
     *      <li> Levý syn = 7, protože 2 * 0 + 1 = 1 (tj. index)
     *      <li> Pravý syn = 5, protože 2 * 0 + 2 = 2 (tj. index)
     *      </ul>
     * <li> Potomci {@code 7}:
     *      <ul>
     *      <li> Levý syn = 1, protože 2 * 1 + 1 = 3 (tj. index)
     *      <li> Pravý syn = 2, protože 2 * 1 + 2 = 4 (tj. index)
     *      </ul>
     * <li> Potomci {@code 5}:
     *      <ul>
     *      <li> Levý syn = 4, protože 2 * 2 + 1 = 5 (tj. index)
     *      </ul>
     * </ul>
     */
    @Test
    public void test_01_04_vybuduj() {
        try {
            Integer[] pole = {5, 2, 9, 1, 7, 4};
            intHalda.vybuduj(pole);

            final int expMohutnost1 = 6;
            final int resMohutnost1 = intHalda.mohutnost();
            assertEquals(expMohutnost1, resMohutnost1);

            final int expected1 = 9;
            final int result1 = intHalda.odeberMax();
            assertEquals(expected1, result1);

            final int expected2 = 7;
            final int result2 = intHalda.odeberMax();
            assertEquals(expected2, result2);

            final int expected3 = 5;
            final int result3 = intHalda.odeberMax();
            assertEquals(expected3, result3);

            final int expected4 = 4;
            final int result4 = intHalda.odeberMax();
            assertEquals(expected4, result4);

            final int expected5 = 2;
            final int result5 = intHalda.odeberMax();
            assertEquals(expected5, result5);

            final int expected6 = 1;
            final int result6 = intHalda.odeberMax();
            assertEquals(expected6, result6);

            final int expMohutnost2 = 0;
            final int resMohutnost2 = intHalda.mohutnost();
            assertEquals(expMohutnost2, resMohutnost2);
        } catch (HeapException ex) {
            fail();
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="reorganizuj(Comparator)">
    @Test
    public void test_02_01_reorganizuj() {
        try {
            intHalda.vloz(1);
            intHalda.vloz(5);
            intHalda.vloz(3);

            final int maxDo = intHalda.zpristupniMax();
            Comparator<Integer> sestupnyKomparator = Comparator.reverseOrder();
            intHalda.reorganizuj(sestupnyKomparator);
            final int maxPo = intHalda.zpristupniMax();

            final int expectedDo = 5;
            assertEquals(expectedDo, maxDo);

            final int expectedPo = 1;
            assertEquals(expectedPo, maxPo);
        } catch (HeapException ex) {
            fail();
        }
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
            final IAbstrHeap<Integer> heap = new AbstrHeap<>(Integer.class, Integer::compareTo, 2);
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
    private final Integer[] pole = new Integer[]{5, 2, 6, 1, 3, 4, 7};

    /**
     * Testuje iterátory do šířky/hloubky (in-order)
     *
     * <p> Reprezentace haldy:
     * <ol>
     * <li> Neupořádaná halda: [5, 2, 6, 1, 3, 4, 7]
     * <li> Uspořádaná halda: [7, 3, 6, 1, 2, 4, 5]
     * <li> Motivační obrázek:
     *             7
     *           /   \
     *          3     6
     *         / \   / \
     *        1   2 4   5
     * <li> Do šířky: 7, 3, 6, 1, 2, 4, 5
     * <li> Do hloubky (in-order): 1, 3, 2, 7, 4, 6, 5
     * <li> Vztahy:
     *      <ol>
     *      <li> Potomci {@code 7}:
     *          <ul>
     *          <li> Levý syn = 3, protože 2 * 0 + 1 = 1 (tj. index)
     *          <li> Pravý syn = 6, protože 2 * 0 + 2 = 2 (tj. index)
     *          </ul>
     *      <li> Potomci {@code 3}:
     *          <ul>
     *          <li> Levý syn = 1, protože 2 * 1 + 1 = 3 (tj. index)
     *          <li> Pravý syn = 2, protože 2 * 1 + 2 = 4 (tj. index)
     *          </ul>
     *      <li> Potomci {@code 6}:
     *          <ul>
     *          <li> Levý syn = 4, protože 2 * 2 + 1 = 5 (tj. index)
     *          <li> Pravý syn = 5, protože 2 * 2 + 2 = 6 (tj. index)
     *          </ul>
     *      </ol>
     * </ol>
     */
    @Test
    public void test_06_01_vypis() {
        try {
            intHalda.vybuduj(pole); // -> [7, 3, 6, 1, 2, 4, 5]

            final int expMosutnost = 7;
            final int resMosutnost = intHalda.mohutnost();
            assertEquals(expMosutnost, resMosutnost);

            final String expSirka = "7, 3, 6, 1, 2, 4, 5";
            final String resSirka = intHalda.vypis(ETypProhl.SIRKA);
            assertEquals(expSirka, resSirka); // -> "7, 3, 6, 1, 2, 4, 5"

            final String expHloubka = "1, 3, 2, 7, 4, 6, 5";
            final String resHloubka = intHalda.vypis(ETypProhl.HLOUBKA);
            assertEquals(expHloubka, resHloubka); // -> "1, 3, 2, 7, 4, 6, 5"

            final int expected1 = 7;
            final int result1 = intHalda.odeberMax();
            assertEquals(expected1, result1);

            final int expected2 = 6;
            final int result2 = intHalda.odeberMax();
            assertEquals(expected2, result2);

            final int expected3 = 5;
            final int result3 = intHalda.odeberMax();
            assertEquals(expected3, result3);

            final int expected4 = 4;
            final int result4 = intHalda.odeberMax();
            assertEquals(expected4, result4);

            final int expected5 = 3;
            final int result5 = intHalda.odeberMax();
            assertEquals(expected5, result5);

            final int expected6 = 2;
            final int result6 = intHalda.odeberMax();
            assertEquals(expected6, result6);

            final int expected7 = 1;
            final int result7 = intHalda.odeberMax();
            assertEquals(expected7, result7);
        } catch (HeapException ex) {
            fail();
        }
    }

    /**
     * Testuje iterátory do šířky/hloubky (in-order) s větším polem
     * <p> Reprezentace číselné haldy:
     * <ol>
     * <li> Neupořádaná halda: [13, 11, 12, 8, 9, 10, 7, 1, 2, 5, 4, 3, 6, 14, 15, 16, 17]
     * <li> Uspořádaná halda: [17, 16, 15, 13, 9, 10, 14, 11, 2, 5, 4, 3, 6, 12, 7, 8, 1]
     * <li> Motivační obrázek:
     *                      17
     *                   /     \
     *                 /         \
     *               /             \
     *             16               15
     *           /    \           /    \
     *         13      9        10      14
     *       /  \     / \      / \      / \
     *     11    2   5   4    3   6   12   7
     *    /  \
     *   8    1
     * <li> Do šířky: 17, 16, 15, 13, 9, 10, 14, 11, 2, 5, 4, 3, 6, 12, 7, 8, 1
     * <li> Do hloubky (in-order): 8, 11, 1, 13, 2, 16, 5, 9, 4, 17, 3, 10, 6, 15, 12, 14, 7
     * </ol>
     *
     * <p> Reprezentace textové haldy:
     * <ol>
     * <li> Neupořádaná halda: [m, k, l, h, i, j, g, a, b, e, d, c, f, n, o, p, q]
     * <li> Uspořádaná halda: [q, p, o, m, i, j, n, k, b, e, d, c, f, l, g, h, a]
     * <li> Motivační obrázek:
     *                      q
     *                   /     \
     *                 /         \
     *               /             \
     *             p                o
     *           /    \           /   \
     *         m       i        j      n
     *       /  \     / \      / \    / \
     *      k    b   e   d    c   f  l   g
     *     / \
     *    h   a
     * <li> Do šířky: q, p, o, m, i, j, n, k, b, e, d, c, f, l, g, h, a
     * <li> Do hloubky (in-order): h, k, a, m, b, p, e, i, d, q, c, j, f, o, l, n, g
     * </ol>
     */
    @Test
    public void test_06_02_vypis() {
        try {
            // --- Integer
            Integer[] intPole = new Integer[]{13, 11, 12, 8, 9, 10, 7, 1, 2, 5, 4, 3, 6, 14, 15, 16, 17};
            intHalda.vybuduj(intPole); // -> [17, 16, 15, 13, 9, 10, 14, 11, 2, 5, 4, 3, 6, 12, 7, 8, 1]

            final String expIntSirka = "17, 16, 15, 13, 9, 10, 14, 11, 2, 5, 4, 3, 6, 12, 7, 8, 1";
            final String resIntSirka = intHalda.vypis(ETypProhl.SIRKA);
            assertEquals(expIntSirka, resIntSirka);

            final String expHloubka = "8, 11, 1, 13, 2, 16, 5, 9, 4, 17, 3, 10, 6, 15, 12, 14, 7";
            final String resHloubka = intHalda.vypis(ETypProhl.HLOUBKA);
            assertEquals(expHloubka, resHloubka);

            // --- String
            String[] strPole = new String[]{"m", "k", "l", "h", "i", "j", "g", "a", "b", "e", "d", "c", "f", "n", "o", "p", "q"};
            strHalda.vybuduj(strPole); // -> [q, p, o, m, i, j, n, k, b, e, d, c, f, l, g, h, a]

            final String expSirka = "q, p, o, m, i, j, n, k, b, e, d, c, f, l, g, h, a";
            final String resSirka = strHalda.vypis(ETypProhl.SIRKA);
            assertEquals(expSirka, resSirka);

            final String expStrHloubka = "h, k, a, m, b, p, e, i, d, q, c, j, f, o, l, n, g";
            final String resStrHloubka = strHalda.vypis(ETypProhl.HLOUBKA);
            assertEquals(expStrHloubka, resStrHloubka);

        } catch (HeapException ex) {
            fail();
        }
    }
// </editor-fold>
}
