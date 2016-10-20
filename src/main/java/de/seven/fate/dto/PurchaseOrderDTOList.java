package de.seven.fate.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class PurchaseOrderDTOList implements Serializable {

    List<PurchaseOrderDTO> purchaseOrders;
}
