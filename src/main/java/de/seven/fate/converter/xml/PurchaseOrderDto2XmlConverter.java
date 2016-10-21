package de.seven.fate.converter.xml;

import de.seven.fate.common.service.JAXBContextService;
import de.seven.fate.converter.AbstractConverter;
import de.seven.fate.dto.PurchaseOrderDto;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.IOException;
import java.io.StringWriter;

@Component
public class PurchaseOrderDto2XmlConverter extends AbstractConverter<String, PurchaseOrderDto> {

    private final JAXBContextService jaxbContextService;

    public PurchaseOrderDto2XmlConverter(JAXBContextService jaxbContextService) {
        this.jaxbContextService = jaxbContextService;
    }

    @Override
    public String convert(PurchaseOrderDto orig) {
        if (orig == null) {
            return null;
        }

        Marshaller marshaller = jaxbContextService.createMarshaller(orig.getClass());

        // output pretty printed
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new IllegalArgumentException(e);
        }

        try (StringWriter writer = new StringWriter()) {

            try {
                marshaller.marshal(orig, writer);
            } catch (JAXBException e) {
                throw new IllegalArgumentException(e);
            }

            return writer.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
