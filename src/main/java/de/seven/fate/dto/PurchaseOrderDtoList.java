package de.seven.fate.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "PurchaseOrders")
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrderDtoList implements Serializable {

    @XmlElement(name = "purchaseOrder", required = true)
    private List<PurchaseOrderDto> purchaseOrder;

}
