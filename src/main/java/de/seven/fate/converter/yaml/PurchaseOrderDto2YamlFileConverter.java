package de.seven.fate.converter.yaml;

import de.seven.fate.converter.AbstractMetaConverter;
import de.seven.fate.dto.PurchaseOrderDto;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Component
public class PurchaseOrderDto2YamlFileConverter extends AbstractMetaConverter<File, PurchaseOrderDto, String> {

    private Yaml mapper = new Yaml();

    @Override
    public File convert(PurchaseOrderDto orig, String outPutDir) {
        if (orig == null) {
            return null;
        }

        File file = new File(outPutDir, "purchase-order-" + orig.getOrderNumber() + ".yml");

        try (Writer writer = new FileWriter(file)) {
            mapper.dump(orig, writer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return file;
    }
}
