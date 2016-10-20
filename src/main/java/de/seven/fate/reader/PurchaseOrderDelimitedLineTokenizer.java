package de.seven.fate.reader;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderDelimitedLineTokenizer extends DelimitedLineTokenizer {

    private static final String[] NAMES = {"ORDER_NUMBER", "ADDRESS_NAME", "ADDRESS_EMAIL", "ITEM_NUMBER", "ITEM_PRODUCT_NAME", "ITEM_QUANTITY", "ITEM_PRICE", "ITEM_GIFT_WRAP"};

    public PurchaseOrderDelimitedLineTokenizer() {
        setNames(NAMES);
    }
}
