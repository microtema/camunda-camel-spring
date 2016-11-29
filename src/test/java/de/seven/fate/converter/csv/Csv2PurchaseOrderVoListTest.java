package de.seven.fate.converter.csv;

import de.seven.fate.vo.PurchaseOrderVo;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Csv2PurchaseOrderVoListTest {

    Csv2PurchaseOrderVoList sut = new Csv2PurchaseOrderVoList();

    @Test
    public void getColumnMapping() throws Exception {

        InputStream inputStream = Csv2PurchaseOrderVoList.class.getResourceAsStream("/purchaseOrders/purchase-order.csv");

        String csv = IOUtils.toString(inputStream);

        List<PurchaseOrderVo> entries = sut.convert(csv);

        assertNotNull(entries);
        assertEquals(9, entries.size());

        entries.forEach((it) -> {
            assertNotNull(it.getItemNumber());
            assertNotNull(it.getItemGiftWrap());
            assertNotNull(it.getAddresEmail());
            assertNotNull(it.getAddresName());
            assertNotNull(it.getItemPrice());
            assertNotNull(it.getItemProductName());
            assertNotNull(it.getItemQuatity());
        });
    }

}
