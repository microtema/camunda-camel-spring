package de.seven.fate.service;

import de.seven.fate.converter.Converter;
import de.seven.fate.dto.PurchaseOrderDTO;
import de.seven.fate.model.PurchaseOrder;
import lombok.extern.java.Log;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Log
@Component
public class PurchaseOrderAggregator implements JavaDelegate {

    @Inject
    private Converter<List<PurchaseOrder>, List<PurchaseOrderDTO>> purchaseOrderDTOList2PurchaseOrderListConverter;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Object purchaseOrdersObj = delegateExecution.getVariable("purchaseOrders");

        List<PurchaseOrderDTO> orderList = (List<PurchaseOrderDTO>) purchaseOrdersObj;

        List<PurchaseOrder> purchaseOrders = purchaseOrderDTOList2PurchaseOrderListConverter.convert(orderList);

        delegateExecution.setVariable("purchaseOrders", purchaseOrders);

        log.info("Aggregate purchase Orders: " + purchaseOrders.size());
    }

}
