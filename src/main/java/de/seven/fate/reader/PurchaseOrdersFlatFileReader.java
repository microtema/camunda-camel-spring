package de.seven.fate.reader;

import de.seven.fate.model.PurchaseOrder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseOrdersFlatFileReader {

    private final FlatFileItemReader<PurchaseOrder> itemReader;
    
    public PurchaseOrdersFlatFileReader(PurchaseOrderFlatFileReader purchaseOrderFlatFileReader) {
        this.itemReader = purchaseOrderFlatFileReader;
    }

    public List<PurchaseOrder> readAll(Resource resource) {

        itemReader.setResource(resource);

        itemReader.open(new ExecutionContext());

        try {
            return readPurchaseOrders();
        } catch (Exception e) {

            itemReader.close();

            throw new IllegalStateException(e);
        }
    }

    private List<PurchaseOrder> readPurchaseOrders() throws Exception {
        PurchaseOrder purchaseOrder;

        List<PurchaseOrder> list = new ArrayList<>();

        while ((purchaseOrder = itemReader.read()) != null) {
            list.add(purchaseOrder);
        }

        return list;
    }
}
