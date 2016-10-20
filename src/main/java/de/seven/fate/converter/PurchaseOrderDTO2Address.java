package de.seven.fate.converter;

import de.seven.fate.dto.PurchaseOrderDTO;
import de.seven.fate.model.Address;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderDTO2Address extends AbstractConverter<Address, PurchaseOrderDTO> {


    public void update(Address dest, PurchaseOrderDTO orig) {
        Validate.notNull(dest);

        dest.setName(orig.getAddresName());
        dest.setEmail(orig.getAddresEmail());
    }
}
