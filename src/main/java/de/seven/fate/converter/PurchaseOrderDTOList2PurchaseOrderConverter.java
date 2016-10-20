package de.seven.fate.converter;

import de.seven.fate.dto.PurchaseOrderDTO;
import de.seven.fate.dto.PurchaseOrderDTOList;
import de.seven.fate.model.Address;
import de.seven.fate.model.PurchaseItem;
import de.seven.fate.model.PurchaseOrder;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseOrderDTOList2PurchaseOrderConverter extends AbstractConverter<PurchaseOrder, PurchaseOrderDTOList> {

    private final Converter<Address, PurchaseOrderDTO> purchaseOrderDTO2Address;
    private final Converter<PurchaseItem, PurchaseOrderDTO> purchaseOrderDTO2PurchaseItem;

    public PurchaseOrderDTOList2PurchaseOrderConverter(PurchaseOrderDTO2Address purchaseOrderDTO2Address, PurchaseOrderDTO2PurchaseItem purchaseOrderDTO2PurchaseItem) {
        this.purchaseOrderDTO2Address = purchaseOrderDTO2Address;
        this.purchaseOrderDTO2PurchaseItem = purchaseOrderDTO2PurchaseItem;
    }

    public void update(PurchaseOrder dest, PurchaseOrderDTOList orig) {
        Validate.notNull(dest);

        List<PurchaseOrderDTO> purchaseOrders = orig.getPurchaseOrders();

        Validate.notEmpty(purchaseOrders);

        PurchaseOrderDTO purchaseOrderDTO = purchaseOrders.stream().findFirst().get();

        dest.setOrderNumber(purchaseOrderDTO.getOrderNumber());
        dest.setAddress(purchaseOrderDTO2Address.convert(purchaseOrderDTO));
        dest.setOrders(purchaseOrderDTO2PurchaseItem.convertList(purchaseOrders));
    }
}
