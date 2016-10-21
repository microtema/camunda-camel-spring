package de.seven.fate.common.service;

import de.seven.fate.dto.PurchaseOrderDto;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Component
public class JAXBContextService {

    public Marshaller createMarshaller(Class<?> rootType) {

        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(PurchaseOrderDto.class);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            return jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
