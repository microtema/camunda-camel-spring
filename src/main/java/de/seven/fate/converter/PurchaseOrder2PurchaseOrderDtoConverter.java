package de.seven.fate.converter;

import com.e2open.model.converter.AbstractConverter;
import de.seven.fate.dto.PurchaseOrderDto;
import de.seven.fate.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang.Validate.notNull;

@Component
public class PurchaseOrder2PurchaseOrderDtoConverter extends AbstractConverter<PurchaseOrderDto, PurchaseOrder> {


    private final Address2AddressDtoConverter address2AddressDTO;
    private final PurchaseItem2PurchaseItemDtoConverter purchaseItem2PurchaseItemDTO;

    public PurchaseOrder2PurchaseOrderDtoConverter(Address2AddressDtoConverter address2AddressDTO, PurchaseItem2PurchaseItemDtoConverter purchaseItem2PurchaseItemDTO) {
        this.address2AddressDTO = address2AddressDTO;
        this.purchaseItem2PurchaseItemDTO = purchaseItem2PurchaseItemDTO;
    }

    @Override
    public void update(PurchaseOrderDto dest, PurchaseOrder orig) {
        notNull(dest);

        if (orig == null) {
            return;
        }

        dest.setOrderNumber(orig.getOrderNumber());
        dest.setAddress(address2AddressDTO.convert(orig.getAddress()));
        dest.setItems(purchaseItem2PurchaseItemDTO.convertList(orig.getItems()));
    }
}
