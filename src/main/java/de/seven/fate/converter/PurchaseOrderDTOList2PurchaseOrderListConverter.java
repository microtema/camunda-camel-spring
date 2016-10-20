package de.seven.fate.converter;

import de.seven.fate.dto.PurchaseOrderDTO;
import de.seven.fate.dto.PurchaseOrderDTOList;
import de.seven.fate.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderDTOList2PurchaseOrderListConverter extends AbstractConverter<List<PurchaseOrder>, List<PurchaseOrderDTO>> {

    private final PurchaseOrderDTOList2PurchaseOrderConverter purchaseOrderDTOList2PurchaseOrderConverter;

    public PurchaseOrderDTOList2PurchaseOrderListConverter(PurchaseOrderDTOList2PurchaseOrderConverter purchaseOrderDTOList2PurchaseOrderConverter) {
        this.purchaseOrderDTOList2PurchaseOrderConverter = purchaseOrderDTOList2PurchaseOrderConverter;
    }

    @Override
    public List<PurchaseOrder> convert(List<PurchaseOrderDTO> orig) {

        if (orig == null) {
            return null;
        }

        Map<String, List<PurchaseOrderDTO>> orderId2PurchaseOrder = aggregatePurchaseOrders(orig);

        List<PurchaseOrderDTOList> purchaseOrderDTOLists = orderId2PurchaseOrder.values().stream().map(orders ->
                PurchaseOrderDTOList.builder().purchaseOrders(orders).build()
        ).collect(Collectors.toList());

        return purchaseOrderDTOList2PurchaseOrderConverter.convertList(purchaseOrderDTOLists);
    }

    private Map<String, List<PurchaseOrderDTO>> aggregatePurchaseOrders(List<PurchaseOrderDTO> orderList) {

        Map<String, List<PurchaseOrderDTO>> orderId2PurchaseOrder = new TreeMap<>();

        orderList.stream().forEach(purchaseOrderDTO -> {

            String orderNumber = purchaseOrderDTO.getOrderNumber();

            List<PurchaseOrderDTO> purchaseOrderDTOs = orderId2PurchaseOrder.get(orderNumber);

            if (purchaseOrderDTOs == null) {
                purchaseOrderDTOs = new ArrayList<>();
                orderId2PurchaseOrder.put(orderNumber, purchaseOrderDTOs);
            }

            purchaseOrderDTOs.add(purchaseOrderDTO);
        });

        return orderId2PurchaseOrder;
    }
}
