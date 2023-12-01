package cz.upce.fei.bdats.perzistence;

// <editor-fold defaultstate="collapsed" desc="Importy">
import cz.upce.fei.bdats.halda.IAbstrHeap;
import cz.upce.fei.bdats.model.Obec;
import cz.upce.fei.bdats.strom.ETypProhl;
import cz.upce.fei.bdats.vyjimky.HeapException;
import cz.upce.fei.bdats.vyjimky.ObecException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Iterator;
// </editor-fold>

/**
 * Třída implementuje sadu základních operací ukládání/načtení datových entit do/z <i>CSV</i> souborů
 *
 * <p> Implementuje rozhraní {@link IPerzistence}
 */
public final class ObecPerzistence implements IPerzistence<Obec> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nactiCsv(IAbstrHeap<Obec> halda, String cesta) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(cesta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] data = line.split(this.ODDELOVAC_ATRIBUTU);
                if (data.length == this.CELKEM_ATRIBUTU) {
                    final int cisloKraje = Integer.parseInt(data[this.INDEX_CISLA_KRAJE]);
                    final String nazevKraje = data[this.INDEX_NAZVU_KRAJE];
                    final String nazevObce = data[this.INDEX_NAZVU_OBCE];
                    final String psc = data[this.INDEX_PSC];
                    final int pocetMuzu = Integer.parseInt(data[this.INDEX_POCTU_MUZU]);
                    final int pocetZen = Integer.parseInt(data[this.INDEX_POCTU_ZEN]);
                    final int celkem = Integer.parseInt(data[this.INDEX_CELKEM]);

                    final Obec obec = new Obec(cisloKraje,
                            nazevKraje,
                            nazevObce,
                            psc,
                            pocetMuzu,
                            pocetZen,
                            celkem);
                    halda.vloz(obec);
                } else {
                    return false;
                }
            }
        } catch (ObecException | HeapException ex) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean ulozCsv(@NotNull IAbstrHeap<Obec> halda) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(IPerzistence.CESTA_ULOZISTE))) {
            final Iterator<Obec> iterator = halda.vytvorIterator(ETypProhl.HLOUBKA);
            while (iterator.hasNext()) {
                final Obec obec = iterator.next();
                writer.write(obec.getCisloKraje() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getNazevKraje() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPsc() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getNazevObce() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPocetMuzu() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getPocetZen() + this.ODDELOVAC_ATRIBUTU);
                writer.write(obec.getCelkem() + this.ODDELOVAC_ATRIBUTU);
                writer.newLine();
            }
        }
        return true;
    }
}
