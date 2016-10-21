package de.seven.fate.reader;

import de.seven.fate.vo.PurchaseOrderVo;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseOrdersFlatFileReader {

    private final FlatFileItemReader<PurchaseOrderVo> itemReader;

    public PurchaseOrdersFlatFileReader(PurchaseOrderFlatFileReader purchaseOrderFlatFileReader) {
        this.itemReader = purchaseOrderFlatFileReader;
    }

    public List<PurchaseOrderVo> readAll(Resource resource) {

        itemReader.setResource(resource);

        itemReader.open(new ExecutionContext());

        try {
            return readPurchaseOrders();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            itemReader.close();
        }
    }

    private List<PurchaseOrderVo> readPurchaseOrders() throws Exception {
        PurchaseOrderVo purchaseOrderDTO;

        List<PurchaseOrderVo> list = new ArrayList<>();

        while ((purchaseOrderDTO = itemReader.read()) != null) {
            list.add(purchaseOrderDTO);
        }

        return list;
    }
}
