package de.seven.fate.reader;

import de.seven.fate.model.PurchaseOrder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderLineMapper extends DefaultLineMapper<PurchaseOrder> {


    public PurchaseOrderLineMapper(PurchaseOrderDelimitedLineTokenizer delimitedLineTokenizer, PurchaseOrderFieldSetMapper fieldSetMapper) {
        setLineTokenizer(delimitedLineTokenizer);
        setFieldSetMapper(fieldSetMapper);
    }
}
