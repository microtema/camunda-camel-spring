package de.seven.fate.converter;

import com.e2open.model.converter.AbstractConverter;
import com.e2open.model.converter.Converter;
import de.seven.fate.model.Address;
import de.seven.fate.model.PurchaseItem;
import de.seven.fate.model.PurchaseOrder;
import de.seven.fate.vo.PurchaseOrderVo;
import de.seven.fate.vo.PurchaseOrderVoList;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseOrderVoList2PurchaseOrderConverter extends AbstractConverter<PurchaseOrder, PurchaseOrderVoList> {

    private final Converter<Address, PurchaseOrderVo> purchaseOrderDTO2Address;
    private final Converter<PurchaseItem, PurchaseOrderVo> purchaseOrderDTO2PurchaseItem;

    public PurchaseOrderVoList2PurchaseOrderConverter(PurchaseOrderVo2AddressConverter purchaseOrderDTO2Address, PurchaseOrderVo2PurchaseItemConverter purchaseOrderDTO2PurchaseItem) {
        this.purchaseOrderDTO2Address = purchaseOrderDTO2Address;
        this.purchaseOrderDTO2PurchaseItem = purchaseOrderDTO2PurchaseItem;
    }

    public void update(PurchaseOrder dest, PurchaseOrderVoList orig) {
        Validate.notNull(dest);

        List<PurchaseOrderVo> purchaseOrders = orig.getPurchaseOrders();

        Validate.notEmpty(purchaseOrders);

        PurchaseOrderVo purchaseOrderDTO = purchaseOrders.stream().findFirst().get();

        dest.setOrderNumber(purchaseOrderDTO.getOrderNumber());
        dest.setAddress(purchaseOrderDTO2Address.convert(purchaseOrderDTO));
        dest.setItems(purchaseOrderDTO2PurchaseItem.convertList(purchaseOrders));
    }
}
