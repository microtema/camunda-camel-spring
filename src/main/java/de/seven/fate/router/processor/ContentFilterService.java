package de.seven.fate.router.processor;

import de.seven.fate.vo.PurchaseOrderVo;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class ContentFilterService implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        List<PurchaseOrderVo> purchaseOrders = message.getBody(List.class);

        List<PurchaseOrderVo> giftPurchaseOrders = purchaseOrders.stream().filter(PurchaseOrderVo::getItemGiftWrap).collect(Collectors.toList());

        exchange.getIn().setBody(giftPurchaseOrders);

        log.info("ContentFilterService.process");
    }
}
