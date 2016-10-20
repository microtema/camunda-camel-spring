package de.seven.fate.reader;

import de.seven.fate.model.PurchaseOrder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderFlatFileReader extends FlatFileItemReader<PurchaseOrder> {


    public PurchaseOrderFlatFileReader(PurchaseOrderLineMapper purchaseOrderLineMapper) {
        setLineMapper(purchaseOrderLineMapper);
    }
}
