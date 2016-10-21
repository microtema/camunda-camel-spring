package de.seven.fate.converter;

import de.seven.fate.model.Address;
import de.seven.fate.vo.PurchaseOrderVo;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderVo2AddressConverter extends AbstractConverter<Address, PurchaseOrderVo> {


    public void update(Address dest, PurchaseOrderVo orig) {
        Validate.notNull(dest);

        dest.setName(orig.getAddresName());
        dest.setEmail(orig.getAddresEmail());
    }
}
