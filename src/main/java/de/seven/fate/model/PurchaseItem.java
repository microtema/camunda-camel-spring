package de.seven.fate.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PurchaseItem implements Serializable{

    private String partNumber;
    private String productName;
    private Integer quatity;
    private BigDecimal price;
    private Boolean giftWrap;
}
