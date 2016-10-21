package de.seven.fate.reader;

import de.seven.fate.vo.PurchaseOrderVo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderFlatFileReader extends FlatFileItemReader<PurchaseOrderVo> {


    public PurchaseOrderFlatFileReader(PurchaseOrderLineMapper purchaseOrderLineMapper) {
        setLineMapper(purchaseOrderLineMapper);
    }
}
