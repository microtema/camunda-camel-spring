package de.seven.fate.reader;

import de.seven.fate.dto.PurchaseOrderDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderFlatFileReader extends FlatFileItemReader<PurchaseOrderDTO> {


    public PurchaseOrderFlatFileReader(PurchaseOrderLineMapper purchaseOrderLineMapper) {
        setLineMapper(purchaseOrderLineMapper);
    }
}
