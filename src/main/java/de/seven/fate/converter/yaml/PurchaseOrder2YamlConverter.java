package de.seven.fate.converter.yaml;

import com.e2open.model.converter.AbstractConverter;
import de.seven.fate.model.PurchaseOrder;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class PurchaseOrder2YamlConverter extends AbstractConverter<String, PurchaseOrder> {

    private Yaml mapper = new Yaml();

    @Override
    public String convert(PurchaseOrder orig) {
        if (orig == null) {
            return null;
        }


        return mapper.dump(orig);
    }
}
