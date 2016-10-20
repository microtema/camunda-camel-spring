package de.seven.fate.converter;

import de.seven.fate.dto.PurchaseOrderDTO;
import de.seven.fate.model.PurchaseItem;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderDTO2PurchaseItem extends AbstractConverter<PurchaseItem, PurchaseOrderDTO> {

    public void update(PurchaseItem dest, PurchaseOrderDTO orig) {
        Validate.notNull(dest);

        dest.setPartNumber(orig.getItemNumber());
        dest.setProductName(orig.getItemProductName());
        dest.setPrice(orig.getItemPrice());
        dest.setQuatity(orig.getItemQuatity());
        dest.setGiftWrap(orig.getItemGiftWrap());
    }
}
