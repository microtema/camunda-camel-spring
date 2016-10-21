package de.seven.fate.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseItemDto implements Serializable {

    @XmlAttribute
    private String partNumber;

    private String productName;
    private Integer quatity;
    private BigDecimal price;
    private Boolean giftWrap;
}
