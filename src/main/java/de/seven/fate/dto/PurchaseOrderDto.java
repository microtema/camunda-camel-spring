package de.seven.fate.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "PurchaseOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrderDto implements Serializable {

    @XmlAttribute
    private String orderNumber;

    private AddressDto address;

    @XmlElement(name = "Items", required = true)
    private List<PurchaseItemDto> items;

}
