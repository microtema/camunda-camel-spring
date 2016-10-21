package de.seven.fate.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PurchaseOrderVo implements Serializable {

    private String orderNumber;
    private String addresName;
    private String addresEmail;
    private String itemNumber;
    private String itemProductName;
    private Integer itemQuatity;
    private BigDecimal itemPrice;
    private Boolean itemGiftWrap;
}
