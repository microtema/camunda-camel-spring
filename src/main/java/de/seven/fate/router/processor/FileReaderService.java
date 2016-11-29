package de.seven.fate.router.processor;

import de.seven.fate.converter.csv.Csv2PurchaseOrderVoList;
import de.seven.fate.vo.PurchaseOrderVo;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log
@Service
public class FileReaderService implements Processor {


    @Inject
    private Csv2PurchaseOrderVoList csv2PurchaseOrderVoList;

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        GenericFileMessage genericFileMessage = (GenericFileMessage) message;

        GenericFile genericFile = genericFileMessage.getGenericFile();

        List<PurchaseOrderVo> purchaseOrderVos = getPurchaseOrders((File) genericFile.getFile());

        exchange.getIn().setBody(purchaseOrderVos);

        log.info("ContentFilterService.process");
    }

    private List<PurchaseOrderVo> getPurchaseOrders(File file) throws IOException {

        try (InputStream inputStream = new FileInputStream(file)) {

            String csv = IOUtils.toString(inputStream);

            return csv2PurchaseOrderVoList.convert(csv);
        }
    }
}
