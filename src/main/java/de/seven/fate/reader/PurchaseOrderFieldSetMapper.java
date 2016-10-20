package de.seven.fate.reader;

import de.seven.fate.model.PurchaseOrder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class PurchaseOrderFieldSetMapper implements FieldSetMapper<PurchaseOrder> {

    @Override
    public PurchaseOrder mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) {
            return null;
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setOrderNumber(fieldSet.readString("ORDER_NUMBER"));

        purchaseOrder.setAddresName(fieldSet.readString("ADDRESS_NAME"));
        purchaseOrder.setAddresEmail(fieldSet.readString("ADDRESS_EMAIL"));

        purchaseOrder.setItemNumber(fieldSet.readString("ITEM_NUMBER"));
        purchaseOrder.setItemProductName(fieldSet.readString("ITEM_PRODUCT_NAME"));
        purchaseOrder.setItemQuatity(fieldSet.readInt("ITEM_QUANTITY"));
        purchaseOrder.setItemPrice(fieldSet.readBigDecimal("ITEM_PRICE"));
        purchaseOrder.setItemGiftWrap(fieldSet.readBoolean("ITEM_GIFT_WRAP"));

        return purchaseOrder;
    }
}
