package strom;

import cz.upce.fei.bdats.halda.AbstrHeap;
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.vyjimky.HeapException;
import org.junit.*;

import java.util.Comparator;

/**
 * Testovací případy pro:
 * <ol>
 * <li> <b>test_01_</b>: Scénáře metody {@link AbstrHeap#vybuduj(Comparable[])}
 * </ol>
 *
 * @author amirov 11/26/2023
 */
public final class AbstrHeapTest {

// <editor-fold defaultstate="collapsed" desc="Nastavení">
    private static final int VYCHOZI_INT_HODNOTA = 1;
    private static final String VYCHOZI_STR_HODNOTA = "a";

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

    @Test
    public void test_01_01_vybuduj() {

    }
}
