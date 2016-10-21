package de.seven.fate.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class PurchaseOrderVoList implements Serializable {

    List<PurchaseOrderVo> purchaseOrders;
}
