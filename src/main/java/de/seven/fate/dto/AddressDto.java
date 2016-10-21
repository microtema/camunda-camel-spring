package de.seven.fate.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDto implements Serializable {

    private String name;
    private String email;
}
