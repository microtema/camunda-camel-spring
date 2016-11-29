package de.seven.fate.router.processor;

import de.seven.fate.model.PurchaseOrder;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class HighPriceContentFilter implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        List<PurchaseOrder> purchaseOrders = message.getBody(List.class);

        List<PurchaseOrder> giftPurchaseOrders = purchaseOrders.stream().filter(it -> {

            Averager averageCollect = it.getItems().stream().map(purchaseItem -> purchaseItem.getPrice().intValue()).collect(Averager::new, Averager::accept, Averager::combine);

            return averageCollect.average() >= 1000;

        }).collect(Collectors.toList());

        exchange.getIn().setBody(giftPurchaseOrders);

        log.info("LowPriceContentFilter.process");
    }


}
