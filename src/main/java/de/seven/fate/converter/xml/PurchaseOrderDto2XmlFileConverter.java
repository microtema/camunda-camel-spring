package de.seven.fate.converter.xml;

import com.e2open.model.converter.AbstractMetaConverter;
import de.seven.fate.common.service.JAXBContextService;
import de.seven.fate.dto.PurchaseOrderDto;
import org.apache.camel.util.FileUtil;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;

@Component
public class PurchaseOrderDto2XmlFileConverter extends AbstractMetaConverter<File, PurchaseOrderDto, String> {

    private final JAXBContextService jaxbContextService;

    public PurchaseOrderDto2XmlFileConverter(JAXBContextService jaxbContextService) {
        this.jaxbContextService = jaxbContextService;
    }

    @Override
    public File convert(PurchaseOrderDto orig, String outPutDir) {
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

        File file = new File(outPutDir, "purchase-order-" + orig.getOrderNumber() + ".xml");

        try {
            marshaller.marshal(orig, file);
        } catch (JAXBException e) {

            FileUtil.deleteFile(file);
            throw new IllegalArgumentException(e);
        }

        return file;
    }
}
