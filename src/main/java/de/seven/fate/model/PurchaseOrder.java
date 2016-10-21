package de.seven.fate.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PurchaseOrder implements Serializable {

    private String orderNumber;
    private Address address;
    private List<PurchaseItem> items;
}
