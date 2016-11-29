package de.seven.fate.converter.json;

import com.e2open.model.converter.AbstractConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.seven.fate.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PurchaseOrder2JsonConverter extends AbstractConverter<String, PurchaseOrder> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convert(PurchaseOrder orig) {
        if (orig == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(orig);
        } catch (IOException e) {

            throw new IllegalArgumentException(e);
        }

    }
}
