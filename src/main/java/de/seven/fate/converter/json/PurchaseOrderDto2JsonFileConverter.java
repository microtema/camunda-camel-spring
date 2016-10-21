package de.seven.fate.converter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.seven.fate.converter.AbstractMetaConverter;
import de.seven.fate.dto.PurchaseOrderDto;
import org.apache.camel.util.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PurchaseOrderDto2JsonFileConverter extends AbstractMetaConverter<File, PurchaseOrderDto, String> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public File convert(PurchaseOrderDto orig, String outPutDir) {
        if (orig == null) {
            return null;
        }

        File file = new File(outPutDir, "purchase-order-" + orig.getOrderNumber() + ".json");

        try {
            mapper.writeValue(file, orig);
        } catch (IOException e) {

            FileUtil.deleteFile(file);
            throw new IllegalArgumentException(e);
        }

        return file;
    }
}
