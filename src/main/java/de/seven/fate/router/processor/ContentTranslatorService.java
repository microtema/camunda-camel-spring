package de.seven.fate.router.processor;

import com.e2open.model.converter.Converter;
import de.seven.fate.model.PurchaseOrder;
import de.seven.fate.vo.PurchaseOrderVo;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Log
@Service
public class ContentTranslatorService implements Processor {

    @Inject
    private Converter<List<PurchaseOrder>, List<PurchaseOrderVo>> purchaseOrderDTOList2PurchaseOrderListConverter;


    @Override
    public void process(Exchange exchange) throws Exception {

        Message message = exchange.getIn();

        List<PurchaseOrderVo> purchaseOrderVoList = message.getBody(List.class);

        List<PurchaseOrder> purchaseOrders = purchaseOrderDTOList2PurchaseOrderListConverter.convert(purchaseOrderVoList);

        exchange.getIn().setBody(purchaseOrders);

        log.info("ContentTranslatorService.process");
    }
}
