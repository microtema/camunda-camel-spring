package de.seven.fate.reader;

import de.seven.fate.vo.PurchaseOrderVo;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderLineMapper extends DefaultLineMapper<PurchaseOrderVo> {


    public PurchaseOrderLineMapper(PurchaseOrderDelimitedLineTokenizer delimitedLineTokenizer, PurchaseOrderFieldSetMapper fieldSetMapper) {
        setLineTokenizer(delimitedLineTokenizer);
        setFieldSetMapper(fieldSetMapper);
    }
}
