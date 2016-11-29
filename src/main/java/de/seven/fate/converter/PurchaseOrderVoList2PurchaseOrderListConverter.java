package de.seven.fate.converter;

import com.e2open.model.converter.AbstractConverter;
import de.seven.fate.model.PurchaseOrder;
import de.seven.fate.vo.PurchaseOrderVo;
import de.seven.fate.vo.PurchaseOrderVoList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderVoList2PurchaseOrderListConverter extends AbstractConverter<List<PurchaseOrder>, List<PurchaseOrderVo>> {

    private final PurchaseOrderVoList2PurchaseOrderConverter purchaseOrderDTOList2PurchaseOrderConverter;

    public PurchaseOrderVoList2PurchaseOrderListConverter(PurchaseOrderVoList2PurchaseOrderConverter purchaseOrderDTOList2PurchaseOrderConverter) {
        this.purchaseOrderDTOList2PurchaseOrderConverter = purchaseOrderDTOList2PurchaseOrderConverter;
    }

    @Override
    public List<PurchaseOrder> convert(List<PurchaseOrderVo> orig) {

        if (orig == null) {
            return null;
        }

        Map<String, List<PurchaseOrderVo>> orderId2PurchaseOrder = aggregatePurchaseOrders(orig);

        List<PurchaseOrderVoList> purchaseOrderDTOLists = orderId2PurchaseOrder.values().stream().map(orders ->
                PurchaseOrderVoList.builder().purchaseOrders(orders).build()
        ).collect(Collectors.toList());

        return purchaseOrderDTOList2PurchaseOrderConverter.convertList(purchaseOrderDTOLists);
    }

    private Map<String, List<PurchaseOrderVo>> aggregatePurchaseOrders(List<PurchaseOrderVo> orderList) {

        Map<String, List<PurchaseOrderVo>> orderId2PurchaseOrder = new TreeMap<>();

        orderList.stream().forEach(purchaseOrderDTO -> {

            String orderNumber = purchaseOrderDTO.getOrderNumber();

            List<PurchaseOrderVo> purchaseOrderDTOs = orderId2PurchaseOrder.get(orderNumber);

            if (purchaseOrderDTOs == null) {
                purchaseOrderDTOs = new ArrayList<>();
                orderId2PurchaseOrder.put(orderNumber, purchaseOrderDTOs);
            }

            purchaseOrderDTOs.add(purchaseOrderDTO);
        });

        return orderId2PurchaseOrder;
    }
}
