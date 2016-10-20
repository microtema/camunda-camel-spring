package de.seven.fate.reader;

import de.seven.fate.dto.PurchaseOrderDTO;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderLineMapper extends DefaultLineMapper<PurchaseOrderDTO> {


    public PurchaseOrderLineMapper(PurchaseOrderDelimitedLineTokenizer delimitedLineTokenizer, PurchaseOrderFieldSetMapper fieldSetMapper) {
        setLineTokenizer(delimitedLineTokenizer);
        setFieldSetMapper(fieldSetMapper);
    }
}
