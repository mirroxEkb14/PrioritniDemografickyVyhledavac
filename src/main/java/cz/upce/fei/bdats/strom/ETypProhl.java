package cz.upce.fei.bdats.strom;

/**
 * Třída je výčtovým typem pro uchovávání preferovaných způsobů, jakými budou iterátory procházet binární vyhledávací
 * strom
 *
 * <p> Třída nabízí hodnoty:
 * <ul>
 * <li> {@link ETypProhl#SIRKA}: Iterátor provádí průchod stromem do šířky <i>(breadth-first traversal)</i>, tzn.
 * nejdříve navštíví všechny uzly na stejné úrovni, než se přesune na další úroveň
 * <li> {@link ETypProhl#HLOUBKA}: Iterátor provádí průchod stromem do hloubky <i>(depth-first traversal)</i>, tzn.
 * postupuje co nejhlouběji do stromu, než se vrátí a postupně projde další uzly
 * </ul>
 */
public enum ETypProhl {
    SIRKA,
    HLOUBKA
}
