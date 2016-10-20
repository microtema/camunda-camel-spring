package de.seven.fate.reader;

import de.seven.fate.dto.PurchaseOrderDTO;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseOrdersFlatFileReader {

    private final FlatFileItemReader<PurchaseOrderDTO> itemReader;

    public PurchaseOrdersFlatFileReader(PurchaseOrderFlatFileReader purchaseOrderFlatFileReader) {
        this.itemReader = purchaseOrderFlatFileReader;
    }

    public List<PurchaseOrderDTO> readAll(Resource resource) {

        itemReader.setResource(resource);

        itemReader.open(new ExecutionContext());

        try {
            return readPurchaseOrders();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            itemReader.close();
        }
    }

    private List<PurchaseOrderDTO> readPurchaseOrders() throws Exception {
        PurchaseOrderDTO purchaseOrderDTO;

        List<PurchaseOrderDTO> list = new ArrayList<>();

        while ((purchaseOrderDTO = itemReader.read()) != null) {
            list.add(purchaseOrderDTO);
        }

        return list;
    }
}
