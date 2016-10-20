package de.seven.fate.reader;

import de.seven.fate.dto.PurchaseOrderDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class PurchaseOrderFieldSetMapper implements FieldSetMapper<PurchaseOrderDTO> {

    @Override
    public PurchaseOrderDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        if (fieldSet == null) {
            return null;
        }

        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

        purchaseOrderDTO.setOrderNumber(fieldSet.readString("ORDER_NUMBER"));

        purchaseOrderDTO.setAddresName(fieldSet.readString("ADDRESS_NAME"));
        purchaseOrderDTO.setAddresEmail(fieldSet.readString("ADDRESS_EMAIL"));

        purchaseOrderDTO.setItemNumber(fieldSet.readString("ITEM_NUMBER"));
        purchaseOrderDTO.setItemProductName(fieldSet.readString("ITEM_PRODUCT_NAME"));
        purchaseOrderDTO.setItemQuatity(fieldSet.readInt("ITEM_QUANTITY"));
        purchaseOrderDTO.setItemPrice(fieldSet.readBigDecimal("ITEM_PRICE"));
        purchaseOrderDTO.setItemGiftWrap(fieldSet.readBoolean("ITEM_GIFT_WRAP"));

        return purchaseOrderDTO;
    }
}
