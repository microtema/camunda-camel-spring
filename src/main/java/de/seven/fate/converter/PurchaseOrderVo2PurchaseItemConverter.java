package de.seven.fate.converter;

import de.seven.fate.model.PurchaseItem;
import de.seven.fate.vo.PurchaseOrderVo;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderVo2PurchaseItemConverter extends AbstractConverter<PurchaseItem, PurchaseOrderVo> {

    public void update(PurchaseItem dest, PurchaseOrderVo orig) {
        Validate.notNull(dest);

        dest.setPartNumber(orig.getItemNumber());
        dest.setProductName(orig.getItemProductName());
        dest.setPrice(orig.getItemPrice());
        dest.setQuatity(orig.getItemQuatity());
        dest.setGiftWrap(orig.getItemGiftWrap());
    }
}
