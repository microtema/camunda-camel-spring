package de.seven.fate.router.processor;

import de.seven.fate.converter.yaml.PurchaseOrder2YamlConverter;
import de.seven.fate.model.PurchaseOrder;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log
@Service
public class YamlTranslatorService implements Processor {

    @Inject
    private PurchaseOrder2YamlConverter purchaseOrder2YamlConverter;

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        List<PurchaseOrder> purchaseOrders = message.getBody(List.class);

        List<String> strings = purchaseOrder2YamlConverter.convertList(purchaseOrders);

        String yaml = StringUtils.join(strings);

        InputStream stream = new ByteArrayInputStream(yaml.getBytes(StandardCharsets.UTF_8));

        exchange.getIn().setBody(stream);
        //exchange.getOut().setBody(stream);

        log.info("YamlTranslatorService.process");
    }
}
