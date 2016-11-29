package de.seven.fate.router.processor;

import de.seven.fate.vo.PurchaseOrderVo;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class ContentEnricherService implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        List<PurchaseOrderVo> purchaseOrders = message.getBody(List.class);

        purchaseOrders.forEach(it -> {
            it.setItemProductName(it.getItemProductName() + ".Super");
        });

        exchange.getIn().setBody(purchaseOrders);

        log.info("ContentEnricherService.process");
    }
}
